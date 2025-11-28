package ru.cwcode.tkach.config.webeditor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.kjetland.jackson.jsonSchema.JsonSchemaConfig;
import com.kjetland.jackson.jsonSchema.JsonSchemaDraft;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.LoaderOptions;
import ru.cwcode.cwutils.config.SimpleConfig;
import ru.cwcode.tkach.config.base.Config;
import ru.cwcode.tkach.config.base.ConfigPersistOptions;
import ru.cwcode.tkach.config.base.manager.ConfigManager;
import ru.cwcode.tkach.config.base.manager.ConfigMapper;
import ru.cwcode.tkach.config.jackson.yaml.YmlConfig;
import ru.cwcode.tkach.config.jackson.yaml.YmlConfigManager;
import spark.Request;
import spark.Spark;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

public class WebEditor {
  public static final JsonSchemaConfig JSON_SCHEMA_CONFIG = JsonSchemaConfig.builder()
                                                                            .jsonSchemaDraft(JsonSchemaDraft.DRAFT_07)
                                                                            .autoGenerateTitleForProperties(true)
                                                                            .defaultArrayFormat("table")
                                                                            .useOneOfForOption(true)
                                                                            .usePropertyOrdering(true)
                                                                            .hidePolymorphismTypeProperty(true)
                                                                            .useMinLengthForNotNull(true)
                                                                            .customType2FormatMapping(JsonSchemaConfig.DEFAULT_DATE_FORMAT_MAPPING)
                                                                            .useMultipleEditorSelectViaProperty(true)
                                                                            .uniqueItemClasses(new HashSet<>() {
                                                                              {
                                                                                this.add(Set.class);
                                                                              }
                                                                            }).build();
  
  List<BiConsumer<YmlConfig, YmlConfig>> onReload = new ArrayList<>();
  int port;
  
  public WebEditor(SimpleConfig config) {
    port = config.get("port", int.class, 2025);
  }
  
  public void addReloadListener(BiConsumer<YmlConfig, YmlConfig> action) {
    onReload.add(action);
  }
  
  public void start() {
    Spark.port(port);
    Spark.staticFileLocation("/public");
    
    Spark.put("/update-yaml/:namespace/*", (request, response) -> {
      String namespace = request.params("namespace");
      String name = request.splat()[0];
      
      ConfigManager<? extends Config<?>> manager = ConfigManager.managers.get(namespace);
      if (manager instanceof YmlConfigManager jcm) {
        YmlConfig ymlConfig = jcm.findConfig(name).orElseThrow();
        String yamlContent = request.body();

        ObjectMapper objectMapper = createObjectMapper();
        jcm.mapper().configureObjectMapper(objectMapper);
        
        YmlConfig newConfig;
        try {
          newConfig = objectMapper.readValue(yamlContent,ymlConfig.getClass());
        } catch (Exception e) {
          return "{\"success\":\"false\"}";
        }
        
        jcm.updateConfig(name, newConfig);
        
        for (BiConsumer<YmlConfig, YmlConfig> listener : onReload) {
          listener.accept(ymlConfig, newConfig);
        }
        
        newConfig.save();
        
        return "{\"success\":\"true\"}";
      }
      
      response.status(500);
      return "Config manager not supported";
    });
    
    Spark.put("/update/:namespace/*", (request, response) -> {
      String namespace = request.params("namespace");
      String name = request.splat()[0];
      
      ConfigManager<? extends Config<?>> manager = ConfigManager.managers.get(namespace);
      if (manager instanceof YmlConfigManager jcm) {
        ObjectMapper objectMapper = new ObjectMapper();
        
        jcm.mapper().configureObjectMapper(objectMapper);
        YmlConfig ymlConfig = jcm.findConfig(name).orElseThrow();
        
        YmlConfig newConfig = objectMapper.readValue(request.body(), ymlConfig.getClass());
        
        jcm.updateConfig(name, newConfig);
        
        for (BiConsumer<YmlConfig, YmlConfig> listener : onReload) {
          listener.accept(ymlConfig, newConfig);
        }
        
        newConfig.save();
        
        return "{\"success\":\"true\"}";
      }
      
      response.status(500);
      return "Config manager not supported";
    });
    
    Spark.get("/", (request, response) -> {
      String basePath = getBasePath(request);
      
      String html = """
        <!DOCTYPE html>
          <html data-lt-installed="true"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
          <link rel="stylesheet" id="theme-link" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css">
          <link rel="stylesheet" id="iconlib-link" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
        
          <body>
            <a href="#" class="btn btn-sm btn-light m-3" onclick="history.back()">
               <i class="bi bi-chevron-left"></i>
               <span class="ms-1">Назад</span>
            </a>
        
            <div class="container mt-4">
                <div class="list-group">
                %s
            </div>
          </body>
        </html>
        """;
      
      StringBuilder elements = new StringBuilder();
      for (String name : ConfigManager.managers.keySet()) {
        String href = basePath + "/index/" + name;
        elements.append("""
                          <a href="%s" class="list-group-item list-group-item-action d-flex align-items-center">
                            <i class="bi bi-file-earmark me-2"></i>%s</a>
                          """.formatted(href, name));
      }
      
      return html.formatted(elements.toString());
    });
    
    Spark.get("/index/:namespace", (request, response) -> {
      String basePath = getBasePath(request);
      
      String namespace = request.params("namespace");
      ConfigManager<? extends Config<?>> manager = ConfigManager.managers.get(namespace);
      String html = """
        <!DOCTYPE html>
          <html data-lt-installed="true"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
          <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css">
          <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
        
          <body>
            <a href="#" class="btn btn-sm btn-light m-3" onclick="history.back()">
               <i class="bi bi-chevron-left"></i>
               <span class="ms-1">Назад</span>
            </a>
        
            <div class="container mt-4">
               <div class="list-group">
               %s
                </div>
              </div>
              <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js"></script>
            </body>
          </html>
        """;
      
      StringBuilder elements = new StringBuilder();
      for (String config : manager.getConfigNames(c -> true)) {
        manager.findConfig(config).ifPresent(configInstance -> {
          String yamlEditorUrl = basePath + "/edit-raw/" + namespace + "/" + config;
          String visualEditorUrl = basePath + "/edit/" + namespace + "/" + config;
          
          elements.append("""
                            <div class="list-group-item list-group-item-action d-flex align-items-center justify-content-between border-start border-4">
                              <a href="%s" class="link-dark text-decoration-none flex-grow-1 d-flex align-items-center">
                                <i class="bi bi-file-earmark-code me-2 text-primary"></i>%s
                              </a>
                              <div class="btn-group btn-group-sm ms-2" role="group">
                                <a href="%s" class="btn btn-outline-primary border-0" title="Визуальный редактор">
                                  <i class="bi bi-ui-checks-grid"></i>
                                </a>
                              </div>
                            </div>
                            """.formatted(yamlEditorUrl, config, visualEditorUrl));
        });
      }
      
      return html.formatted(elements.toString());
    });
    
    Spark.get("/edit-raw/:namespace/*", (request, response) -> {
      String namespace = request.params("namespace");
      String name = request.splat()[0];
      String basePath = getBasePath(request);
      
      ConfigManager<? extends Config<?>> manager = ConfigManager.managers.get(namespace);
      if (!(manager instanceof YmlConfigManager jcm))
        throw new IllegalArgumentException("This config manager not supported");
      
      ObjectMapper objectMapper = createObjectMapper();
      jcm.mapper().configureObjectMapper(objectMapper);
      
      YmlConfig ymlConfig = jcm.findConfig(name).orElseThrow();
      
      JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(objectMapper, JSON_SCHEMA_CONFIG);
      JsonNode schema = jsonSchemaGenerator.generateJsonSchema(ymlConfig.getClass());
      
      String currentYaml = objectMapper.writeValueAsString(ymlConfig);
      
      return """
        <!DOCTYPE html>
        <html lang="ru">
        	<head>
        		<meta charset="UTF-8" />
        		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
        		<title>YAML Editor - %s</title>
        
        		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" />
        
        		<style>
        			* {
        			  box-sizing: border-box;
        			}
        			html, body {
        			  width: 100%%;
        			  height: 100%%;
        			  margin: 0;
        			  padding: 0;
        			  overflow: hidden;
        			}
        			body {
        			  background-color: #1e1e1e;
        			  color: #cccccc;
        			  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", "Roboto", "Oxygen", "Ubuntu", "Cantarell", "Fira Sans", "Droid Sans", "Helvetica Neue", sans-serif;
        			  font-size: 13px;
        			}
        
        			/* Monaco-style container */
        			.editor-wrapper {
        			  position: fixed;
        			  top: 0;
        			  left: 0;
        			  width: 100%%;
        			  height: 100%%;
        			}
        
        			#editor {
        			  width: 100%%;
        			  height: 100%%;
        			}
        
        			/* Toolbar - Monaco style */
        			.toolbar {
        			  position: absolute;
        			  top: 0;
        			  left: 0;
        			  right: 0;
        			  height: 35px;
        			  display: flex;
        			  justify-content: space-between;
        			  align-items: center;
        			  padding: 0 12px;
        			  background-color: #252526;
        			  border-bottom: 1px solid #3e3e42;
        			  z-index: 1000;
        			  gap: 12px;
        			}
        
        			.toolbar-left {
        			  display: flex;
        			  align-items: center;
        			  gap: 8px;
        			}
        
        			.toolbar-right {
        			  display: flex;
        			  align-items: center;
        			  gap: 8px;
        			}
        
        			/* Monaco buttons */
        			.monaco-btn {
        			  display: inline-flex;
        			  align-items: center;
        			  justify-content: center;
        			  height: 24px;
        			  padding: 0 8px;
        			  border: 1px solid #3e3e42;
        			  border-radius: 2px;
        			  background-color: #0e639c;
        			  color: #cccccc;
        			  font-size: 12px;
        			  font-weight: 500;
        			  cursor: pointer;
        			  transition: all 0.15s ease;
        			  gap: 4px;
        			  white-space: nowrap;
        			}
        
        			.monaco-btn:hover:not(:disabled) {
        			  background-color: #1177bb;
        			  border-color: #0e7c0e;
        			}
        
        			.monaco-btn:active:not(:disabled) {
        			  background-color: #094771;
        			}
        
        			.monaco-btn:disabled {
        			  opacity: 0.5;
        			  cursor: not-allowed;
        			}
        
        			.monaco-btn.secondary {
        			  background-color: #3e3e42;
        			  border-color: #464646;
        			}
        
        			.monaco-btn.secondary:hover:not(:disabled) {
        			  background-color: #464646;
        			}
        
        			.monaco-btn i {
        			  font-size: 14px;
        			}
        
        			.badge {
        			  display: inline-flex;
        			  align-items: center;
        			  height: 20px;
        			  padding: 0 8px;
        			  background-color: #0e639c;
        			  color: #cccccc;
        			  border-radius: 2px;
        			  font-size: 11px;
        			  font-weight: 500;
        			}
        
        			/* Toasts - Monaco style */
        			.toast-container {
        			  position: fixed;
        			  bottom: 20px;
        			  right: 20px;
        			  z-index: 2000;
        			}
        
        			.toast {
        			  min-width: 300px;
        			  background-color: #3c3c3c;
        			  border: 1px solid #464646;
        			  border-radius: 2px;
        			  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.3);
        			  margin-bottom: 8px;
        			  display: none;
        			  animation: slideIn 0.2s ease;
        			}
        
        			@keyframes slideIn {
        			  from {
        			    opacity: 0;
        			    transform: translateX(10px);
        			  }
        			  to {
        			    opacity: 1;
        			    transform: translateX(0);
        			  }
        			}
        
        			.toast-header {
        			  display: flex;
        			  align-items: center;
        			  justify-content: space-between;
        			  padding: 8px 12px;
        			  border-bottom: 1px solid #464646;
        			  font-size: 12px;
        			  font-weight: 500;
        			}
        
        			.toast-header.success {
        			  background-color: #0e639c;
        			  color: #cccccc;
        			}
        
        			.toast-header.error {
        			  background-color: #a1260d;
        			  color: #cccccc;
        			}
        
        			.toast-body {
        			  padding: 12px;
        			  font-size: 12px;
        			  color: #cccccc;
        			}
        
        			.toast-close {
        			  background: none;
        			  border: none;
        			  color: #cccccc;
        			  cursor: pointer;
        			  padding: 0 4px;
        			  font-size: 14px;
        			  opacity: 0.7;
        			  transition: opacity 0.15s;
        			}
        
        			.toast-close:hover {
        			  opacity: 1;
        			}
        		</style>
        	</head>
        	<body>
        		<!-- Toasts -->
        		<div class="toast-container">
        			<div id="successToast" class="toast">
        				<div class="toast-header success">
        					<strong>✓ Успешно!</strong>
        					<button class="toast-close" onclick="this.closest('.toast').style.display='none'">&times;</button>
        				</div>
        				<div class="toast-body">Данные успешно обновлены!</div>
        			</div>
        
        			<div id="errorToast" class="toast">
        				<div class="toast-header error">
        					<strong>✕ Ошибка!</strong>
        					<button class="toast-close" onclick="this.closest('.toast').style.display='none'">&times;</button>
        				</div>
        				<div class="toast-body" id="errorMessage">Произошла ошибка при обновлении данных</div>
        			</div>
        		</div>
        
        		<!-- Toolbar -->
        		<div class="toolbar">
        			<div class="toolbar-left">
        				<button class="monaco-btn secondary" onclick="history.back();" title="Вернуться назад">
        					<i class="bi bi-chevron-left"></i>
        					<span>Назад</span>
        				</button>
        			</div>
        			<div class="toolbar-right">
        				<span class="badge">%s / %s</span>
        				<button class="monaco-btn" id="submit" title="Сохранить (Ctrl+S)">
        					<i class="bi bi-save"></i>
        					<span>Сохранить</span>
        				</button>
        			</div>
        		</div>
        
        		<!-- Editor -->
        		<div class="editor-wrapper">
        			<div id="editor"></div>
        		</div>
        
        		<script src="%s/monaco/monaco-editor.js"></script>
        
        		<script>
        			var BASE_PATH   = "%s";
        			var NAMESPACE   = "%s";
        			var CONFIG_NAME = "%s";
        			var currentYaml = "%s";
        			var schema      = %s;
        
        			var yamlModelUri = monaco.Uri.parse('inmemory://model/' + CONFIG_NAME + '.yaml');
        
        			var diagnosticsOptions = {
        			  enableSchemaRequest: false,
        			  hover: true,
        			  completion: true,
        			  validate: true,
        			  format: true,
        			  schemas: [
        			    {
        			      fileMatch: ['*'],
        			      uri: 'inmemory://schema/' + CONFIG_NAME + '.json',
        			      schema: schema
        			    }
        			  ]
        			};
        
        			monacoYaml.configureMonacoYaml(monaco, diagnosticsOptions);
        
        			var model = monaco.editor.createModel(
        			  currentYaml,
        			  'yaml',
        			  yamlModelUri
        			);
        
        			var editor = monaco.editor.create(document.getElementById('editor'), {
        			  model: model,
        			  theme: 'vs-dark',
        			  automaticLayout: true,
        			  fontSize: 13,
        			  tabSize: 2,
        			  insertSpaces: true,
        			  minimap: { enabled: false },
        			  scrollBeyondLastLine: false,
        			  wordWrap: 'on',
        			  formatOnPaste: true,
        			  formatOnType: true,
        			  lineNumbers: 'on',
        			  renderWhitespace: 'none',
        			  padding: { top: 40 }
        			});
        
        			editor.addCommand(monaco.KeyMod.CtrlCmd | monaco.KeyCode.KeyS, function() {
        			  document.getElementById('submit').click();
        			});
        
        			document.getElementById('submit').addEventListener('click', function() {
        			  const yamlContent = editor.getValue();
        
        			  fetch(BASE_PATH + '/update-yaml/' + NAMESPACE + '/' + CONFIG_NAME, {
        			    method: 'PUT',
        			    headers: {
        			      'Content-Type': 'text/plain; charset=utf-8'
        			    },
        			    body: yamlContent
        			  })
        			  .then(response => {
        			    if (!response.ok) {
        			      return response.text().then(err => { throw new Error(err); });
        			    }
        			    return response.json();
        			  })
        			  .then(result => {
        			    const toast = document.getElementById('successToast');
        			    toast.style.display = 'block';
        			    setTimeout(() => { toast.style.display = 'none'; }, 5000);
        			  })
        			  .catch(error => {
        			    const toast = document.getElementById('errorToast');
        			    document.getElementById('errorMessage').textContent = error.message || 'Неизвестная ошибка';
        			    toast.style.display = 'block';
        			    setTimeout(() => { toast.style.display = 'none'; }, 10000);
        			  });
        			});
        		</script>
        	</body>
        </html>
        
    """.formatted(
        name,                         // title
        namespace,                    // badge: namespace
        name,                         // badge: config name
        basePath,                     // <script src="BASE_PATH/monaco/monaco-editor.js">
        basePath,                     // JS: BASE_PATH
        namespace,                    // JS: NAMESPACE
        name,                         // JS: CONFIG_NAME
        escapeJsonString(currentYaml),// JS: currentYaml
        schema.toPrettyString()       // JS: schema (как JSON-объект, без кавычек)
      );
    });
    
    
    
    Spark.get("/edit/:namespace/*", (request, response) -> {
      String namespace = request.params("namespace");
      String name = request.splat()[0];
      String basePath = getBasePath(request);
      
      ConfigManager<? extends Config<?>> manager = ConfigManager.managers.get(namespace);
      if (!(manager instanceof YmlConfigManager jcm))
        throw new IllegalArgumentException("This config manager not supported");
      ObjectMapper objectMapper = new ObjectMapper();
      jcm.mapper().configureObjectMapper(objectMapper);
      
      YmlConfig ymlConfig = jcm.findConfig(name).orElseThrow();
      
      JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(objectMapper);
      JsonNode schema = jsonSchemaGenerator.generateJsonSchema(ymlConfig.getClass());
      
      return """
        <!DOCTYPE html>
        <html data-lt-installed="true"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
          <script src="https://cdn.jsdelivr.net/npm/@json-editor/json-editor@latest/dist/jsoneditor.js"></script>
          <link rel="stylesheet" id="theme-link" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css">
          <link rel="stylesheet" id="iconlib-link" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
          <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        
          <body>
            <div class="toast-container position-fixed bottom-0 end-0 p-3">
              <div id="successToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true" data-bs-delay="5000">
                <div class="toast-header bg-success text-white">
                  <strong class="me-auto">Успешно!</strong>
                  <button type="button" class="btn-close btn-close-white" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
                <div class="toast-body">
                  Данные успешно обновлены!
                </div>
              </div>
        
              <div id="errorToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true" data-bs-delay="10000">
                <div class="toast-header bg-danger text-white">
                  <strong class="me-auto">Ошибка!</strong>
                  <button type="button" class="btn-close btn-close-white" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
                <div class="toast-body" id="errorMessage">
                  Произошла ошибка при обновлении данных
                </div>
              </div>
            </div>
        
            <a href="#" class="btn btn-sm btn-light m-3" onclick="history.back()">
               <i class="bi bi-chevron-left"></i>
               <span class="ms-1">Назад</span>
            </a>
        
            <div id="editor_holder" class="m-5"></div>
        
            <button class="btn btn-primary m-3" id="submit">
                <i class="bi bi-save me-2"></i>
                Сохранить
            </button>
        
            <script>
            var BASE_PATH = "%s";
            var starting = %s;
            var schema = %s;
            var editor = new JSONEditor(document.getElementById('editor_holder'), {
                schema: schema,
                theme: 'bootstrap5',
                iconlib: 'bootstrap',
                startval: starting,
                use_default_values: true,
                use_name_attributes: true,
                prompt_before_delete: true,
                show_opt_in: true,
                enable_array_copy: true,
                remove_button_labels: true,
                show_errors: "interaction",
                disable_properties: true,
                no_additional_properties: true
            });
        
            document.getElementById('submit').addEventListener('click', function() {
               const data = editor.getValue();
        
               fetch(BASE_PATH + '/update/%s/%s', {
                 method: 'PUT',
                 headers: {
                   'Content-Type': 'application/json'
                 },
                 body: JSON.stringify(data)
               })
               .then(response => {
                 if (!response.ok) {
                   return response.json().then(err => { throw err; });
                 }
                 return response.json();
               })
               .then(result => {
                 const successToast = new bootstrap.Toast(document.getElementById('successToast'));
                 successToast.show();
        
                 console.log('Success:', result);
               })
               .catch(error => {
                 const errorToast = new bootstrap.Toast(document.getElementById('errorToast'));
                 document.getElementById('errorMessage').textContent = error.message || 'Неизвестная ошибка';
                 errorToast.show();
        
                 console.error('Error:', error);
               });
             });
        
             const toastElList = [].slice.call(document.querySelectorAll('.toast'))
             const toastList = toastElList.map(function(toastEl) {
               return new bootstrap.Toast(toastEl)
             });
           </script>
          </body>
        </html>
        """.formatted(
        basePath,
        objectMapper.writeValueAsString(ymlConfig),
        schema.toPrettyString(),
        namespace,
        name
      );
    });
  }
  
  private static @NotNull ObjectMapper createObjectMapper() {
    LoaderOptions loaderOptions = new LoaderOptions();
    loaderOptions.setCodePointLimit(100 * 1024 * 1024);
    
    YAMLFactory yaml = YAMLFactory.builder()
                                  .disable(YAMLGenerator.Feature.SPLIT_LINES)
                                  .disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID)
                                  .disable(YAMLGenerator.Feature.USE_NATIVE_OBJECT_ID)
                                  .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES)
                                  .enable(YAMLGenerator.Feature.INDENT_ARRAYS_WITH_INDICATOR)
                                  .loaderOptions(loaderOptions)
                                  .build();
    
    return new ObjectMapper(yaml);
  }
  
  private static String getBasePath(Request request) {
    String prefix = request.headers("X-Forwarded-Prefix");
    if (prefix != null && !prefix.isBlank()) {
      if (prefix.endsWith("/")) {
        prefix = prefix.substring(0, prefix.length() - 1);
      }
      return prefix;
    }
    return "";
  }
  
  private static String escapeJsonString(String str) {
    return str.replace("\\", "\\\\")
              .replace("\"", "\\\"")
              .replace("\n", "\\n")
              .replace("\r", "\\r")
              .replace("\t", "\\t");
  }
}
