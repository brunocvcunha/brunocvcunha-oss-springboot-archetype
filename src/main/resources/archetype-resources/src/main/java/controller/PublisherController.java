package ${package}.controller;

import ${package}.Application;
import ${package}.model.CurrentUser;
import ${package}.model.Post;
import ${package}.service.PostService;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.brunocvcunha.doormain.model.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PublisherController {

    private final static Logger log = Logger.getLogger(PublisherController.class);

    @Autowired
    private PostService postService;

    @RequestMapping(method = RequestMethod.GET, value = "/publisher/publish")
    public String provideUploadInfo(Model model) {
        return "publisher/publish";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/publisher/publish")
    @PreAuthorize("hasRole('PUBLISHER')")
    public String handleFileUpload(@RequestParam("text") String text, 
            @RequestParam(value = "subscription", required = false) Long subscriptionId,
            @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal Principal principal) {
        if (!file.getContentType().equalsIgnoreCase("image/jpeg")
                && !file.getContentType().equalsIgnoreCase("image/png")) {
            redirectAttributes.addFlashAttribute("message",
                    "Erro ao publicar " + text + ", tipo de arquivo não permitido.");
            return "redirect:/publisher/publish";
        }
        CurrentUser activeUser = (CurrentUser) ((Authentication) principal).getPrincipal();

        String uuid = UUID.randomUUID().toString();

        return "redirect:/publisher/publish";
    }

}