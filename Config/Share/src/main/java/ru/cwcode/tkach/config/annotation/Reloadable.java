package ru.cwcode.tkach.config.annotation;

//Yes, it's in the wrong package, but if I move it, it breaks all the dependent plugins :(
public interface Reloadable {
  boolean reload();
}
