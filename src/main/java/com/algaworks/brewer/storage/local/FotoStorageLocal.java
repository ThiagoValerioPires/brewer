package com.algaworks.brewer.storage.local;

import static java.nio.file.FileSystems.getDefault;

import com.algaworks.brewer.storage.FotoStorage;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Slf4j
public class FotoStorageLocal implements FotoStorage {

    private Path local;
    private Path localTemporario;

    public FotoStorageLocal(){
        this(getDefault().getPath(System.getenv("HOME"), ".brewerfotos"));
    }

    public FotoStorageLocal(Path path){
        this.local = path;
        criarPastas();
    }

    @Override
    public String salvarTemporariamente(MultipartFile[] files) {
        String novoNome = null;
        if(files != null && files.length > 0) {
            MultipartFile arquivo = files[0];
            novoNome = renomearArquivo(arquivo.getOriginalFilename());
            try {
                arquivo.transferTo(new File(localTemporario.toAbsolutePath().toString() + getDefault().getSeparator() + novoNome));
            } catch (IOException e) {
                throw new RuntimeException("Erro salvando a foto na pasta temporaria", e);
            }
        }

        return novoNome;
    }

    @Override
    public byte[] recuperarFotoTemporaria(String nome) {
        try {
            return Files.readAllBytes(this.localTemporario.resolve(nome));
        } catch (IOException e) {
            throw new RuntimeException("Erro lendo a foto temporaria", e);
        }
    }

    @Override
    public void salvar(String foto) {
        try {
            Files.move(this.localTemporario.resolve(foto),this.local.resolve(foto));
        } catch (IOException e) {
            throw new RuntimeException("Erro movendo a foto para destino final");
        }
        try {
            Thumbnails.of(this.local.resolve(foto).toString()).size(40,68).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
        } catch (IOException e) {
            throw new RuntimeException("Erro gerando thumbnail");
        }
    }

    @Override
    public byte[] recuperar(String foto) {
        try {
            return Files.readAllBytes(this.local.resolve(foto));
        } catch (IOException e) {
            throw new RuntimeException("Erro lendo a foto", e);
        }
    }

    private void criarPastas() {
        try {
            Files.createDirectories(local);
            localTemporario = getDefault().getPath(this.local.toString(), "temp");
            Files.createDirectories(this.localTemporario);

            log.debug("Pastas criadas para salvar fotos");
            log.debug("Pasta default {}", local.toAbsolutePath());
            log.debug("Pasta temporaria {}", localTemporario.toAbsolutePath());

        } catch (IOException e) {
            throw  new RuntimeException("Erro criando pasta para salvar foto");
        }
    }

    private String renomearArquivo( String nomeOriginal){
        String novoNome = UUID.randomUUID().toString() + "_" + nomeOriginal;
        log.debug("Nome original: {}, novo nome: {}", nomeOriginal, novoNome);
        return novoNome;
    }


}
