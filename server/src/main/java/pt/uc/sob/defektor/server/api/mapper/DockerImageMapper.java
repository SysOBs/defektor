package pt.uc.sob.defektor.server.api.mapper;

import pt.uc.sob.defektor.server.api.data.DockerImageData;
import pt.uc.sob.defektor.server.model.DockerImage;

public class DockerImageMapper {

    public static DockerImageData convertToDAO(DockerImage dockerImage) {
        DockerImageData dockerImageData = new DockerImageData();
        dockerImageData.setName(dockerImage.getName());
        dockerImageData.setTag(dockerImage.getTag());
        dockerImageData.setUser(dockerImage.getUser());

        return dockerImageData;
    }

    public static DockerImage convertToDTO(DockerImageData dockerImageData) {
        DockerImage dockerImage = new DockerImage();
        dockerImage.setName(dockerImageData.getName());
        dockerImage.setTag(dockerImageData.getTag());
        dockerImage.setUser(dockerImageData.getUser());

        return dockerImage;
    }
}
