package com.opencore.asciidoctor;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.extension.JavaExtensionRegistry;
import org.asciidoctor.extension.spi.ExtensionRegistry;

public class DrawioMacroExtension implements ExtensionRegistry {
  @Override
  public void register(Asciidoctor asciidoctor) {
    final JavaExtensionRegistry javaExtensionRegistry = asciidoctor.javaExtensionRegistry();
    javaExtensionRegistry.block("drawio", new DrawioMacro("drawio", null) );

  }
}
