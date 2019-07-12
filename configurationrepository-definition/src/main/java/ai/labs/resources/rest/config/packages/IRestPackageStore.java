package ai.labs.resources.rest.config.packages;

import ai.labs.models.DocumentDescriptor;
import ai.labs.models.PackageConfiguration;
import ai.labs.resources.rest.IRestVersionInfo;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * @author ginccc
 */
@Api(value = "Configurations -> (3) Packages", authorizations = {@Authorization(value = "eddi_auth")})
@Path("/packagestore/packages")
public interface IRestPackageStore extends IRestVersionInfo {
    String resourceURI = "eddi://ai.labs.package/packagestore/packages/";

    @GET
    @Path("/jsonSchema")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponse(code = 200, response = Map.class, message = "JSON Schema (for validation).")
    @ApiOperation(value = "Read JSON Schema for package definition.")
    Response readJsonSchema();

    @GET
    @Path("descriptors")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Read list of package descriptors.")
    List<DocumentDescriptor> readPackageDescriptors(@QueryParam("filter") @DefaultValue("") String filter,
                                                    @QueryParam("index") @DefaultValue("0") Integer index,
                                                    @QueryParam("limit") @DefaultValue("20") Integer limit);

    @POST
    @Path("descriptors")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Read list of package descriptors including a given resourceUri.")
    List<DocumentDescriptor> readPackageDescriptors(
            @QueryParam("filter") @DefaultValue("") String filter,
            @QueryParam("index") @DefaultValue("0") Integer index,
            @QueryParam("limit") @DefaultValue("20") Integer limit,
            @ApiParam(name = "body", value = "eddi://ai.labs.TYPE/PATH/ID?version=VERSION")
            @DefaultValue("") String containingResourceUri,
            @QueryParam("includePreviousVersions") @DefaultValue("false") Boolean includePreviousVersions);

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Read package.")
    PackageConfiguration readPackage(@PathParam("id") String id,
                                     @ApiParam(name = "version", required = true, format = "integer", example = "1")
                                     @QueryParam("version") Integer version);

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Update package.")
    Response updatePackage(@PathParam("id") String id,
                           @ApiParam(name = "version", required = true, format = "integer", example = "1")
                           @QueryParam("version") Integer version, PackageConfiguration packageConfiguration);

    @PUT
    @Path("/{id}/updateResourceUri")
    @Consumes(MediaType.TEXT_PLAIN)
    @ApiOperation(value = "Update references to other resources within this package resource.")
    Response updateResourceInPackage(@PathParam("id") String id,
                                     @ApiParam(name = "version", required = true, format = "integer", example = "1")
                                     @QueryParam("version") Integer version, URI resourceURI);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create package.")
    Response createPackage(PackageConfiguration packageConfiguration);

    @POST
    @Path("/{id}")
    @ApiOperation(value = "Duplicate this package.")
    Response duplicatePackage(@PathParam("id") String id,
                              @QueryParam("version") Integer version,
                              @QueryParam("deepCopy") @DefaultValue("false") Boolean deepCopy);

    @DELETE
    @Path("/{id}")
    @ApiOperation(value = "Delete package.")
    Response deletePackage(@PathParam("id") String id,
                           @ApiParam(name = "version", required = true, format = "integer", example = "1")
                           @QueryParam("version") Integer version);
}
