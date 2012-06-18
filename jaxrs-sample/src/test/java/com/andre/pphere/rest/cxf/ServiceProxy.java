package com.andre.pphere.rest.cxf;

/**
 * Service Proxy.
 * See: http://cxf.apache.org/docs/jax-rs-client-api.html
 */
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.*;
import com.andre.pphere.data.Store;
import com.andre.pphere.data.StoreList;

public interface ServiceProxy {
    @GET
    @Path("store")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public StoreList getStores();
}
