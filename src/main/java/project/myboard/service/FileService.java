package project.myboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import project.myboard.dto.FileDto;
import project.myboard.entity.FileEntity;
import project.myboard.repository.FileRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${upload.path}")
    private String fileDir;

    private final FileRepository fileRepository;

    @Transactional
    public Long saveFile(FileDto fileDto){

        return fileRepository.save(fileDto.toEntity()).getId();
    }

    @Transactional
    public FileDto getFile(Long id){
        FileEntity file = fileRepository.findById(id).get();

        FileDto fileDto = FileDto.builder()
                .id(id)
                .originFileName(file.getOriginFileName())
                .savedFileName(file.getSavedFileName())
                .uploadDir(file.getUploadDir())
                .build();

        return fileDto;

    }
}
