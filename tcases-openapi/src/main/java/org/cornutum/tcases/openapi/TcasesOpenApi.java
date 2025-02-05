/// //////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2019, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////
package org.cornutum.tcases.openapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;
import org.cornutum.tcases.SystemInputDef;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Defines methods for converting between OpenAPI models and Tcases models.
 * <P/>
 * OpenAPI models must conform to <U>OAS version 3</U>. See <A href="https://swagger.io/specification/#specification">https://swagger.io/specification/#specification</A>.
 */
public final class TcasesOpenApi {
    /**
     * Creates a new TcasesOpenApi instance.
     */
    private TcasesOpenApi() {
        // Static methods only
    }

    /**
     * Returns a {@link SystemInputDef system input definition} for the API requests defined by the given
     * OpenAPI definition. Returns null if the given definition defines no API requests to model.
     */
    public static SystemInputDef getRequestInputModel(OpenAPI api) {
        return getRequestInputModel(api, null);
    }

    /**
     * Returns a {@link SystemInputDef system input definition} for the API requests defined by the given
     * OpenAPI definition. Returns null if the given definition defines no API requests to model.
     */
    public static SystemInputDef getRequestInputModel(OpenAPI api, ModelOptions options) {
        RequestInputModeller inputModeller = new RequestInputModeller(options);
        if (options != null) {
            customOpenAPI(api, options.getExtensionFile());
        }
        return inputModeller.getRequestInputModel(api);
    }

    /**
     * Returns a {@link SystemInputDef system input definition} for the API responses defined by the given
     * OpenAPI definition. Returns null if the given definition defines no API responses to model.
     */
    public static SystemInputDef getResponseInputModel(OpenAPI api) {
        return getResponseInputModel(api, null);
    }

    /**
     * Returns a {@link SystemInputDef system input definition} for the API responses defined by the given
     * OpenAPI definition. Returns null if the given definition defines no API responses to model.
     */
    public static SystemInputDef getResponseInputModel(OpenAPI api, ModelOptions options) {
        ResponseInputModeller inputModeller = new ResponseInputModeller(options);
        return inputModeller.getResponseInputModel(api);
    }

    /**
     * Returns a {@link SystemInputDef system input definition} for the API requests defined examples in the given
     * OpenAPI definition. Returns null if the given definition defines no API requests to model.
     */
    public static SystemInputDef getRequestExamplesModel(OpenAPI api) {
        return getRequestExamplesModel(api, null);
    }

    /**
     * Returns a {@link SystemInputDef system input definition} for the API requests defined examples in the given
     * OpenAPI definition. Returns null if the given definition defines no API requests to model.
     */
    public static SystemInputDef getRequestExamplesModel(OpenAPI api, ModelOptions options) {
        return getRequestInputModel(api, ModelOptions.builder(options).source(ModelOptions.Source.EXAMPLES).build());
    }

    /**
     * 使用给定的扩展文件对OpenAPI进行自定义
     */
    private static void customOpenAPI(OpenAPI api, File extensionFile) {
        if (extensionFile == null) {
            // 没给定扩展文件，就不用自定义了
            return;
        }

        Map<?, ?> extMap;
        try {
            extMap = new ObjectMapper().reader().readValue(extensionFile, Map.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read extension file " + extensionFile.getAbsolutePath(), e);
        }

        if (extMap != null) {
            String[] pathPlaceholders = new String[extMap.size()];
            int i = 0;
            for (Object path : extMap.keySet()) {
                pathPlaceholders[i++] = "{" + path + "}";
            }

            Paths paths = api.getPaths();
            Set<String> pathSet = paths.keySet();
            pathSet.forEach(path -> {
                for (String pathPlaceholder : pathPlaceholders) {
                    if (path.contains(pathPlaceholder)) {
                        // TODO 还需要更多检查
                        paths.get(path).addExtension("x-business-cases",
                                extMap.get(pathPlaceholder.substring(1, pathPlaceholder.length() - 1)));
                    }
                }
            });
        }
    }
}
