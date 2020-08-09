package com.example.springboot;

import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServiceController {
  private static final Config config = new ConfigBuilder().build();
  private static final KubernetesClient client = new DefaultKubernetesClient( config );

  @RequestMapping( value = "/services", produces = MediaType.TEXT_HTML_VALUE )
  public String index() {
    List serviceList = client.services().list().getItems();
    String htmlReponse = "";
    for ( Object svc : serviceList ) {
      String svcName =  ((Service) svc).getMetadata().getName();
      htmlReponse += "<li style=\"text-align: left;\">" + svcName + "</li>";
    }
    return htmlReponse;
  }
}
