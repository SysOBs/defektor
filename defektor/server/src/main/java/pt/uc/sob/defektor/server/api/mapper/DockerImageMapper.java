package pt.uc.sob.defektor.server.api.mapper;

import pt.uc.sob.defektor.server.api.data.DockerImageData;
import pt.uc.sob.defektor.server.model.DockerImage;

public class DockerImageMapper {

    public static DockerImage convertToDAO(DockerImageData dockerImageData) {
        DockerImage dockerImage = new DockerImage();
        dockerImage.setName(dockerImageData.getName());
        dockerImage.setTag(dockerImageData.getTag());
        dockerImage.setUser(dockerImageData.getUser());

        return dockerImage;
    }

    public static DockerImageData convertToDTO(DockerImage dockerImage) {
        DockerImageData dockerImageData = new DockerImageData();
        dockerImageData.setName(dockerImage.getName());
        dockerImageData.setTag(dockerImage.getTag());
        dockerImageData.setUser(dockerImage.getUser());

        return dockerImageData;
    }
}
