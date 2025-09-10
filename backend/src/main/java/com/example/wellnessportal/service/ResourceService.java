package com.example.wellnessportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wellnessportal.model.Resource;
import com.example.wellnessportal.repository.ResourceRepository;

@Service
public class ResourceService {
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
         List<Resource> resourceList = new java.util.ArrayList<>();
       for(Resource resource : resourceRepository.findAll())
       {
        if(resource.getResourceCategory().equalsIgnoreCase(category))
        {
            resourceList.add(resource);
        }
       }
       return resourceList;
    }

    public List<Resource> getResourcesByTags(String tags)
    {
         List<Resource> resourceList = new java.util.ArrayList<>();
       for(Resource resource : resourceRepository.findAll())
       {
        for (String tag : resource.getResourceTags())
        {
            if(tag.equalsIgnoreCase(tags))
            {
                resourceList.add(resource);
            }
        }
       }
       return resourceList;
    }

    public List<Resource> getResourcesByTitle(String title)
    {
         List<Resource> resourceList = new java.util.ArrayList<>();
       for(Resource resource : resourceRepository.findAll())
       {
        if(resource.getTitle().toLowerCase().contains(title.toLowerCase()))
        {
            resourceList.add(resource);
        }
       }
       return resourceList;
    }

    public Resource getResourceById(Long resourceId)
    {
       
       for(Resource resource : resourceRepository.findAll())
       {
        if(resource.getResourceId().equals(resourceId))
        {
            return resource;
        }
       }
       return null;
    }

   //The following operations can only be performed by the admin
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
   }

}
