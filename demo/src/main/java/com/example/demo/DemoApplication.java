package com.example.demo;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.core.DockerClientBuilder;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {
    
        @RequestMapping("/hello")
        public String hello(){
            try{
                DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://127.0.0.1:2375").build();
                System.out.println("dockerclient - hello\n");
                List<Container> containers = dockerClient.listContainersCmd().exec();
                System.out.println("containers size - "+containers.size());
                
                List<Image> images = dockerClient.listImagesCmd().exec();
                System.out.println("images size - "+images.size());
                for(Image i : images){
                    System.out.println("image--"+i.getId());
                    String[] repotags = i.getRepoTags();
                    System.out.println("repotags zieze - "+repotags.length);
                    for(int j = 0; j<repotags.length; j++){
                        System.out.println("    - "+repotags[j]);
                    }
                }
                
            } catch(Exception ex){
                System.out.println("exc in conn");
                ex.printStackTrace();
            }
            
            return "Welcome to docker";
        }
        
        @RequestMapping("/what")
        public String doWhat(){
            System.out.println("running on docker");
            return "nothing to do";
        }

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
