package ru.cwcode.tkach.config.webeditor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;
import ru.cwcode.cwutils.config.SimpleConfig;
import ru.cwcode.tkach.config.base.Config;
import ru.cwcode.tkach.config.base.manager.ConfigManager;
import ru.cwcode.tkach.config.jackson.yaml.YmlConfig;
import ru.cwcode.tkach.config.jackson.yaml.YmlConfigManager;
import spark.Spark;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class WebEditor {
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
    
    Spark.put("/update/:namespace/:name", (request, response) -> {
      String namespace = request.params("namespace");
      String name = request.params("name");
      
      ConfigManager<? extends Config<?>> manager = ConfigManager.managers.get(namespace);
      if (manager instanceof YmlConfigManager jcm) {
        ObjectMapper objectMapper = new ObjectMapper();
        
        jcm.mapper().configureObjectMapper(objectMapper);
        YmlConfig ymlConfig = jcm.findConfig(name).orElseThrow();
        
        YmlConfig newConfig = objectMapper.readValue(request.body(), ymlConfig.getClass());
        
        jcm.updateConfig(name, newConfig);
        
        for (BiConsumer<YmlConfig, YmlConfig> listener : onReload) {
          listener.accept(ymlConfig,newConfig);
        }
        
        newConfig.save();
        
        return "{\"success\":\"true\"}";
      }
      
      response.status(500);
      return "Config manager not supported";
    });
    
    Spark.get("/", (request, response) -> {
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
        elements.append("""
                          <a href="%s" class="list-group-item list-group-item-action d-flex align-items-center">
                                    <i class="bi bi-file-earmark me-2"></i>%s</a>""".formatted("/index/" + name, name));
      }
      
      return html.formatted(elements.toString());
    });
    
    Spark.get("/index/:namespace", (request, response) -> {
      String namespace = request.params("namespace");
      ConfigManager<? extends Config<?>> manager = ConfigManager.managers.get(namespace);
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
      for (String config : manager.getConfigNames(config -> true)) {
        manager.findConfig(config).ifPresent(configInstance -> {
          elements.append("""
                            <a href="%s" class="list-group-item list-group-item-action d-flex align-items-center">
                                      <i class="bi bi-file-earmark me-2"></i>%s</a>""".formatted("/edit/" + namespace + "/" + config, config));
        });
      }
      
      return html.formatted(elements.toString());
    });
    
    Spark.get("/edit/:namespace/:name", (request, response) -> {
      String namespace = request.params("namespace");
      String name = request.params("name");
      
      ConfigManager<? extends Config<?>> manager = ConfigManager.managers.get(namespace);
      if (!(manager instanceof YmlConfigManager jcm)) throw new IllegalArgumentException("This config manager not supported");
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

               fetch('/update/%s/%s', {
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
        """.formatted(objectMapper.writeValueAsString(ymlConfig),
                      schema.toPrettyString(),
                      namespace,
                      name);
    });
  }
}
