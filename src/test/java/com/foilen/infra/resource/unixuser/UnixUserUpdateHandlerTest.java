/*
    Foilen Infra Resource Unix User
    https://github.com/foilen/foilen-infra-resource-unixuser
    Copyright (c) 2018 Foilen (http://foilen.com)

    The MIT License
    http://opensource.org/licenses/MIT

 */
package com.foilen.infra.resource.unixuser;

import org.junit.Assert;
import org.junit.Test;

import com.foilen.infra.plugin.core.system.fake.junits.AbstractIPPluginTest;
import com.foilen.infra.plugin.v1.core.context.ChangesContext;
import com.foilen.infra.plugin.v1.core.service.IPResourceService;

public class UnixUserUpdateHandlerTest extends AbstractIPPluginTest {

    private UnixUser findUnixUser(String name) {
        IPResourceService resourceService = getCommonServicesContext().getResourceService();
        return resourceService.resourceFind(resourceService.createResourceQuery(UnixUser.class).propertyEquals(UnixUser.PROPERTY_NAME, name)).orElse(null);
    }

    @Test
    public void testCreatingWithKeepPassword() {

        // User
        UnixUser unixUser = new UnixUser();
        unixUser.setName("the_user");
        unixUser.setPassword("the_password");
        unixUser.setKeepClearPassword(true);

        // Add
        ChangesContext changes = new ChangesContext(getCommonServicesContext().getResourceService());
        changes.resourceAdd(unixUser);
        getInternalServicesContext().getInternalChangeService().changesExecute(changes);

        // Assert
        UnixUser actual = findUnixUser("the_user");
        Assert.assertEquals("the_user", actual.getName());
        Assert.assertEquals("/home/the_user", actual.getHomeFolder());
        Assert.assertEquals("the_password", actual.getPassword());
        Assert.assertTrue(actual.isKeepClearPassword());
        Assert.assertNotNull(actual.getHashedPassword());
        Assert.assertEquals("/bin/bash", actual.getShell());

    }

    @Test
    public void testCreatingWithNoPassword() {

        // User
        UnixUser unixUser = new UnixUser();
        unixUser.setName("the_user");
        unixUser.setKeepClearPassword(true);

        // Add
        ChangesContext changes = new ChangesContext(getCommonServicesContext().getResourceService());
        changes.resourceAdd(unixUser);
        getInternalServicesContext().getInternalChangeService().changesExecute(changes);

        // Assert
        UnixUser actual = findUnixUser("the_user");
        Assert.assertEquals("the_user", actual.getName());
        Assert.assertEquals("/home/the_user", actual.getHomeFolder());
        Assert.assertNull(actual.getPassword());
        Assert.assertTrue(actual.isKeepClearPassword());
        Assert.assertNull(actual.getHashedPassword());
        Assert.assertEquals("/bin/bash", actual.getShell());

    }

    @Test
    public void testCreatingWithoutKeepPassword() {

        // User
        UnixUser unixUser = new UnixUser();
        unixUser.setName("the_user");
        unixUser.setPassword("the_password");
        unixUser.setKeepClearPassword(false);

        // Add
        ChangesContext changes = new ChangesContext(getCommonServicesContext().getResourceService());
        changes.resourceAdd(unixUser);
        getInternalServicesContext().getInternalChangeService().changesExecute(changes);

        // Assert
        UnixUser actual = findUnixUser("the_user");
        Assert.assertEquals("the_user", actual.getName());
        Assert.assertEquals("/home/the_user", actual.getHomeFolder());
        Assert.assertNull(actual.getPassword());
        Assert.assertFalse(actual.isKeepClearPassword());
        Assert.assertNotNull(actual.getHashedPassword());
        Assert.assertEquals("/bin/bash", actual.getShell());

    }

    @Test
    public void testUpdatingPassword() {

        // ---------- Create ----------
        // User
        UnixUser unixUser = new UnixUser();
        unixUser.setName("the_user");
        unixUser.setPassword("the_password");
        unixUser.setKeepClearPassword(true);

        // Add
        ChangesContext changes = new ChangesContext(getCommonServicesContext().getResourceService());
        changes.resourceAdd(unixUser);
        getInternalServicesContext().getInternalChangeService().changesExecute(changes);

        // Assert
        UnixUser actual = findUnixUser("the_user");
        Assert.assertEquals("the_user", actual.getName());
        Assert.assertEquals("/home/the_user", actual.getHomeFolder());
        Assert.assertEquals("the_password", actual.getPassword());
        Assert.assertTrue(actual.isKeepClearPassword());
        Assert.assertNotNull(actual.getHashedPassword());
        Assert.assertEquals("/bin/bash", actual.getShell());

        // ---------- Update with same password (same hash) ----------
        String currentHash = actual.getHashedPassword();
        actual.setPassword("the_password");
        changes = new ChangesContext(getCommonServicesContext().getResourceService());
        changes.resourceUpdate(actual);
        getInternalServicesContext().getInternalChangeService().changesExecute(changes);

        actual = findUnixUser("the_user");
        Assert.assertEquals("the_user", actual.getName());
        Assert.assertEquals("/home/the_user", actual.getHomeFolder());
        Assert.assertEquals("the_password", actual.getPassword());
        Assert.assertTrue(actual.isKeepClearPassword());
        Assert.assertNotNull(actual.getHashedPassword());
        Assert.assertEquals("/bin/bash", actual.getShell());
        Assert.assertEquals(currentHash, actual.getHashedPassword());

        // ---------- Update with different password ----------
        currentHash = actual.getHashedPassword();
        actual.setPassword("the_password_2");
        changes = new ChangesContext(getCommonServicesContext().getResourceService());
        changes.resourceUpdate(actual);
        getInternalServicesContext().getInternalChangeService().changesExecute(changes);

        actual = findUnixUser("the_user");
        Assert.assertEquals("the_user", actual.getName());
        Assert.assertEquals("/home/the_user", actual.getHomeFolder());
        Assert.assertEquals("the_password_2", actual.getPassword());
        Assert.assertTrue(actual.isKeepClearPassword());
        Assert.assertNotNull(actual.getHashedPassword());
        Assert.assertEquals("/bin/bash", actual.getShell());
        Assert.assertNotEquals(currentHash, actual.getHashedPassword());

        // ---------- Update without keeping password ----------
        currentHash = actual.getHashedPassword();
        actual.setKeepClearPassword(false);
        changes = new ChangesContext(getCommonServicesContext().getResourceService());
        changes.resourceUpdate(actual);
        getInternalServicesContext().getInternalChangeService().changesExecute(changes);

        actual = findUnixUser("the_user");
        Assert.assertEquals("the_user", actual.getName());
        Assert.assertEquals("/home/the_user", actual.getHomeFolder());
        Assert.assertNull(actual.getPassword());
        Assert.assertFalse(actual.isKeepClearPassword());
        Assert.assertNotNull(actual.getHashedPassword());
        Assert.assertEquals("/bin/bash", actual.getShell());
        Assert.assertEquals(currentHash, actual.getHashedPassword());

        // ---------- Update with same password (same hash) ----------
        currentHash = actual.getHashedPassword();
        actual.setPassword("the_password_2");
        changes = new ChangesContext(getCommonServicesContext().getResourceService());
        changes.resourceUpdate(actual);
        getInternalServicesContext().getInternalChangeService().changesExecute(changes);

        actual = findUnixUser("the_user");
        Assert.assertEquals("the_user", actual.getName());
        Assert.assertEquals("/home/the_user", actual.getHomeFolder());
        Assert.assertNull(actual.getPassword());
        Assert.assertFalse(actual.isKeepClearPassword());
        Assert.assertNotNull(actual.getHashedPassword());
        Assert.assertEquals("/bin/bash", actual.getShell());
        Assert.assertEquals(currentHash, actual.getHashedPassword());

        // ---------- Update with different password ----------
        currentHash = actual.getHashedPassword();
        actual.setPassword("the_password");
        changes = new ChangesContext(getCommonServicesContext().getResourceService());
        changes.resourceUpdate(actual);
        getInternalServicesContext().getInternalChangeService().changesExecute(changes);

        actual = findUnixUser("the_user");
        Assert.assertEquals("the_user", actual.getName());
        Assert.assertEquals("/home/the_user", actual.getHomeFolder());
        Assert.assertNull(actual.getPassword());
        Assert.assertFalse(actual.isKeepClearPassword());
        Assert.assertNotNull(actual.getHashedPassword());
        Assert.assertEquals("/bin/bash", actual.getShell());
        Assert.assertNotEquals(currentHash, actual.getHashedPassword());

        // ---------- Delete ----------
        changes = new ChangesContext(getCommonServicesContext().getResourceService());
        changes.resourceDelete(actual);
        getInternalServicesContext().getInternalChangeService().changesExecute(changes);

        Assert.assertNull(findUnixUser("the_user"));

    }

}