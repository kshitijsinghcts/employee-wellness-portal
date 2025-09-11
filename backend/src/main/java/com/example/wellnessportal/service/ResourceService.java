package com.example.wellnessportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wellnessportal.model.Resource;
import com.example.wellnessportal.repository.ResourceRepository;

@Service
public class ResourceService 
{
    @Autowired
    private ResourceRepository resourceRepository;
   
    /** Methods of searching resources:
     * 1. By Title or keywords in title
     * 2. By Category
     * 3. By Tags
     * 4. By Resource Id
     */
    public List<Resource> getAllResources()
    {
        return resourceRepository.findAll();
    }

    public List<Resource> getResourcesByCategory(String category)
    {
        return resourceRepository.findResourceByResourceCategory(category);
    }


    public List<Resource> getResourcesByTags(String tags)
    {
        return resourceRepository.findResourcesByTag(tags);
    }


    public List<Resource> getResourcesByTitle(String title)
    {
        return resourceRepository.findResourcesByTitleContaining(title);
    }

    public Resource getResourceById(Long resourceId)
    {
        return resourceRepository.findResourceById(resourceId);
    }
       

   //The following operations can only be performed by the admin
   // This should be configured in the controller classes
   public void addResource(Resource resource)
   {
    resourceRepository.save(resource);
   }
   
   public void deleteResource(Long resourceId)
   {
    Resource resource = getResourceById(resourceId);
    if(resource!=null)
    {
        resourceRepository.delete(resource);
    }
    else
    {
        //Handle in controller and display to user and redirect him
        throw new IllegalArgumentException("Resource with ID " + resourceId + " not found.");
    }
   }

}
