package ai.labs.resources.rest.config.packages;

import ai.labs.models.DocumentDescriptor;
import ai.labs.models.PackageConfiguration;
import ai.labs.persistence.IResourceStore;

import java.util.List;

/**
 * @author ginccc
 */
public interface IPackageStore extends IResourceStore<PackageConfiguration> {
    List<DocumentDescriptor> getPackageDescriptorsContainingResource(String resourceURI, boolean includePreviousVersions) throws ResourceStoreException, ResourceNotFoundException;
}
