package project.myboard.dto;

import lombok.*;
import project.myboard.entity.FileEntity;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FileDto {

    private Long id;
    private String originFileName;
    private String savedFileName;
    private String uploadDir;

    @Builder
    public FileDto(Long id, String originFileName, String savedFileName, String uploadDir){
        this.id = id;
        this.originFileName = originFileName;
        this.savedFileName = savedFileName;
        this.uploadDir = uploadDir;
    }

    public FileEntity toEntity(){
        return FileEntity.builder()
                .id(id)
                .originFileName(originFileName)
                .savedFileName(savedFileName)
                .uploadDir(uploadDir)
                .build();
    }


}
