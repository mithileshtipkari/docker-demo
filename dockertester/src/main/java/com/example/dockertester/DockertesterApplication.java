package com.example.dockertester;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.SearchItem;
import com.github.dockerjava.core.DockerClientBuilder;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DockertesterApplication {
    
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
                
                Info info = dockerClient.infoCmd().exec();
                System.out.print("info -\n"+info);
                
//                List<SearchItem> dockerSearch = dockerClient.searchImagesCmd("dockerdemo").exec();
//                System.out.println("Search returned" + dockerSearch.toString());

//                  CreateContainerResponse containerdemo = dockerClient.createContainerCmd("dockerdemo").exec();
//                    System.out.println("containerdemo -"+containerdemo.getId());
//                    dockerClient.startContainerCmd(containerdemo.getId()).exec();
//                    dockerClient.stopContainerCmd(container.getId()).exec();
                    //dockerClient.waitContainerCmd(container.getId()).exec();
                    
                    List<Container> containersafter = dockerClient.listContainersCmd().withShowAll(true).exec();
                System.out.println("containers size - "+containersafter.size());
                for(Container c: containersafter){
                    System.out.println("con status - "+c.getStatus());
                    System.out.println("c.id - "+c.getId());
                    dockerClient.startContainerCmd(c.getId()).exec();
                }
            } catch(Exception ex){
                System.out.println("exc in conn");
                ex.printStackTrace();
            }
            
            return "Welcome to docker";
        }
    
        @RequestMapping("/makeContainer")
        public String makeContainer(){
            
            return "ok";
        }
	public static void main(String[] args) {
		SpringApplication.run(DockertesterApplication.class, args);
	}

}
