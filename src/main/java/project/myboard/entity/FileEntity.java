//package project.myboard.entity;
//
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Getter
//@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class FileEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String originFileName;
//
//    @Column(nullable = false)
//    private String fullPath;
//
//    @Builder
//    public FileEntity(Long id, String originFileName, String fullPath) {
//        this.id = id;
//        this.originFileName = originFileName;
//        this.fullPath = fullPath;
//    }
//}
