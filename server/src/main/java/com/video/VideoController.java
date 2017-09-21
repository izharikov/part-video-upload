package com.video;

import com.video.upload.service.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Ihar Zharykau
 */
@Controller
public class VideoController {
    private FilesService filesService;

    @Autowired
    public VideoController(FilesService filesService) {
        this.filesService = filesService;
    }

    @RequestMapping("/videos")
    public String videos(Model model) {
        model.addAttribute("videos", filesService
                .listAllFiles());
        return "videos";
    }

    @RequestMapping("/video")
    public String video(@RequestParam("name") String videoName){
        return "video";
    }
}
