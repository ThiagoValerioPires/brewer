package com.algaworks.brewer.storage;

import com.algaworks.brewer.dto.FotoDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
public class FotoStorageRunnable implements Runnable {

    private MultipartFile[] files;
    private DeferredResult<FotoDTO> resultado;
    private FotoStorage fotoStorage;


    @Override
    public void run() {
        String nome = this.fotoStorage.salvarTemporariamente(files);
        String contentType = files[0].getContentType();
        resultado.setResult(new FotoDTO(nome, contentType));
    }
}
