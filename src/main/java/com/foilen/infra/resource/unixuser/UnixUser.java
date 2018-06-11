/*
    Foilen Infra Resource Unix User
    https://github.com/foilen/foilen-infra-resource-unixuser
    Copyright (c) 2018 Foilen (http://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.infra.resource.unixuser;

import com.foilen.infra.plugin.v1.model.resource.AbstractIPResource;
import com.foilen.infra.plugin.v1.model.resource.InfraPluginResourceCategory;
import com.google.common.collect.ComparisonChain;

/**
 * This is a unix user that is installed on some Machine.<br>
 * Links to:
 * <ul>
 * <li>Machine: (optional / many) INSTALLED_ON - The machines where to install that unix user</li>
 * </ul>
 *
 * Manages:
 * <ul>
 * <li>None</li>
 * </ul>
 */
public class UnixUser extends AbstractIPResource implements Comparable<UnixUser> {

    public static final String PROPERTY_ID = "id";
    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_HOME_FOLDER = "homeFolder";
    public static final String PROPERTY_SHELL = "shell";
    public static final String PROPERTY_HASHED_PASSWORD = "hashedPassword";

    private Long id;
    private String name;
    private String homeFolder;
    private String shell = "/bin/bash";
    private String hashedPassword;

    public UnixUser() {
    }

    public UnixUser(Long id, String name, String homeFolder, String shell, String hashedPassword) {
        this.id = id;
        this.name = name;
        this.homeFolder = homeFolder;
        this.shell = shell;
        this.hashedPassword = hashedPassword;
    }

    @Override
    public int compareTo(UnixUser o) {
        return ComparisonChain.start() //
                .compare(this.name, o.name) //
                .result();
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getHomeFolder() {
        return homeFolder;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public InfraPluginResourceCategory getResourceCategory() {
        return InfraPluginResourceCategory.INFRASTRUCTURE;
    }

    @Override
    public String getResourceDescription() {
        return homeFolder;
    }

    @Override
    public String getResourceName() {
        return name;
    }

    public String getShell() {
        return shell;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setHomeFolder(String homeFolder) {
        this.homeFolder = homeFolder;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShell(String shell) {
        this.shell = shell;
    }

}
