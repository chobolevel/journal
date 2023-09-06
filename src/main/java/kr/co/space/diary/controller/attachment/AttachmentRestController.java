package kr.co.space.diary.controller.attachment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/attachment")
public class AttachmentRestController {

  @Value("${spring.servlet.multipart.location}")
  private String basePath;

  @GetMapping("/{id}/{name}")
  public ResponseEntity<Resource> download(@PathVariable("id") String diaryId, @PathVariable("name") String fileName) throws IOException {
    Path path = Paths.get(basePath + "/" + diaryId + "/" + fileName);
    String contentType = Files.probeContentType(path);

    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_TYPE, contentType);
    String contentDisposition = String.format("attachment;filename=%s", URLEncoder.encode(fileName));
    headers.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition);

    InputStreamResource resource = new InputStreamResource(Files.newInputStream(path));

    return ResponseEntity.ok()
        .headers(headers)
        .body(resource);
  }

}
