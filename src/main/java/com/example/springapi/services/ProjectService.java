package com.example.springapi.services;

import com.example.springapi.domain.Project;
import com.example.springapi.exceptions.ProjectIdException;
import com.example.springapi.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public Project saveProject(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception ex) {
            throw new ProjectIdException("Producto con Id: " + project.getProjectIdentifier().toUpperCase() + " ya existe.");
        }
    }

    public Project findProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if (project == null) {
            throw new ProjectIdException("Producto " + projectId + " no existe");
        }
        return project;
    }

    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if (project == null) {
            throw new ProjectIdException("No se pudo encontrar proyecto con id " + projectId);
        }
        projectRepository.delete(project);
    }

    public Project updateProject(Project project) {
        project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
        Project oldProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
        if (oldProject == null) {
            throw new ProjectIdException("No se pudo modificar proyecto con id: " + project.getProjectIdentifier());
        }
        project.setId(oldProject.getId());
        return projectRepository.save(project);
    }
}
