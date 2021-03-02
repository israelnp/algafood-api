package com.algaworks.algafood.api.model.input;

import com.algaworks.algafood.core.validation.FileSize;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FotoProdutoInput {
    @NotNull
    @FileSize(max = "500KB")
    private MultipartFile arquivo;
    private String descricao;

}