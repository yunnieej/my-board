//package project.myboard.dto;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import project.myboard.entity.FileEntity;
//
//import javax.persistence.Column;
//
//@Getter
//@Setter
//@NoArgsConstructor
//public class FileDto {
//
//    private Long id;
//
//    private String originFileName;
//
//    private String fullPath;
//
//    public FileEntity toFileEntity(){
//        FileEntity fileEntity = FileEntity.builder()
//                .id(id)
//                .originFileName(originFileName)
//                .fullPath(fullPath)
//                .build();
//
//        return fileEntity;
//    }
//
//    public FileDto(Long id, String originFileName, String fullPath) {
//        this.id = id;
//        this.originFileName = originFileName;
//        this.fullPath = fullPath;
//    }
//}
