package com.example.springapi.web;
import com.example.springapi.domain.Project;
import com.example.springapi.services.MapValidationErrorServices;
import com.example.springapi.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationErrorServices mapValidationErrorServices;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorServices.MapValidationService(result);
        if (errorMap != null) return errorMap;
        Project project1 = projectService.saveProject(project);
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectId(@PathVariable String projectId) {
        Project project = projectService.findProjectByIdentifier(projectId);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project> getAllProjects() {
        return projectService.findAllProjects();
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId) {
        projectService.deleteProjectByIdentifier(projectId);
        return new ResponseEntity<String>("Proyecto con id "+ projectId + " eliminado correctamente", HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<?> updateProject(@Valid @RequestBody Project project, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorServices.MapValidationService(result);
        if (errorMap != null) return errorMap;
        Project projectUpdated = projectService.updateProject(project);
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

}
