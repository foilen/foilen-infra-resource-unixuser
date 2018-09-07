/*
    Foilen Infra Resource Unix User
    https://github.com/foilen/foilen-infra-resource-unixuser
    Copyright (c) 2018 Foilen (http://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.infra.resource.unixuser;

import java.util.Arrays;

import com.foilen.infra.plugin.v1.core.context.CommonServicesContext;
import com.foilen.infra.plugin.v1.core.plugin.IPPluginDefinitionProvider;
import com.foilen.infra.plugin.v1.core.plugin.IPPluginDefinitionV1;
import com.foilen.infra.resource.unixuser.helper.UnixUserAvailableIdHelper;

public class FoilenUnixUserPluginDefinitionProvider implements IPPluginDefinitionProvider {

    @Override
    public IPPluginDefinitionV1 getIPPluginDefinition() {
        IPPluginDefinitionV1 pluginDefinitionV1 = new IPPluginDefinitionV1("Foilen", "Unix User", "To manage unix users", "1.0.0");

        pluginDefinitionV1.addCustomResource(UnixUser.class, UnixUser.RESOURCE_TYPE, //
                Arrays.asList(UnixUser.PROPERTY_ID), //
                Arrays.asList( //
                        UnixUser.PROPERTY_NAME, //
                        UnixUser.PROPERTY_HOME_FOLDER, //
                        UnixUser.PROPERTY_SHELL //
                ));

        pluginDefinitionV1.addTranslations("/com/foilen/infra/resource/unixuser/messages");
        pluginDefinitionV1.addResourceEditor(new UnixUserEditor(), UnixUserEditor.EDITOR_NAME);

        pluginDefinitionV1.addUpdateHandler(new UnixUserUpdateHandler());

        return pluginDefinitionV1;
    }

    @Override
    public void initialize(CommonServicesContext commonServicesContext) {
        UnixUserAvailableIdHelper.init(commonServicesContext.getResourceService());
    }

}
