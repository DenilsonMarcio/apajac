package com.apajac.acolhimento.services;

import com.apajac.acolhimento.domain.entities.AssistidoEntity;
import com.apajac.acolhimento.domain.entities.FamiliarEntity;
import com.apajac.acolhimento.domain.entities.UsuarioEntity;
import com.apajac.acolhimento.repositories.*;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BackupService {

    private final AssistidoRepository assistidoRepository;
    private final AuditoriaRepository auditoriaRepository;
    private final CarsRepository carsRepository;
    private final ComposicaoFamiliarRepository composicaoFamiliarRepository;
    private final ContatoRepository contatoRepository;
    private final FamiliarRepository familiarRepository;
    private final MChatRepository mChatRepository;
    private final ResponsavelRepository responsavelRepository;
    private final RespostaCarsRepository respostaCarsRepository;
    private final RespostaMChatRepository respostaMChatRepository;
    private final UsuarioRepository usuarioRepository;

    public byte[] gerarBackupExcelComSenha(String senha) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            criarAbaUsuarios(workbook);
            criarAbaAssistidos(workbook);
//            criarAbaContatos(workbook);
//            criarAbaFamiliares(workbook);
//            criarAbaResponsaveis(workbook);

            workbook.write(outputStream);
            byte[] excelBytes = outputStream.toByteArray();

            return excelBytes;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void criarAbaUsuarios(Workbook workbook) {
        Sheet sheet = workbook.createSheet("Usuarios");
        List<UsuarioEntity> usuarios = usuarioRepository.findAll();
        String[] colunas = {
                "ID",
                "Nome",
                "Login",
                "Password",
                "status",
                "Roles"
        };

        preencherDados(sheet, colunas, usuarios, usuario -> new String[]{
                usuario.getId().toString(),
                usuario.getNome(),
                usuario.getLogin(),
                usuario.getPassword(),
                String.valueOf(usuario.isStatus()),
                usuario.getRoles().toString()
        });
    }

    private void criarAbaAssistidos(Workbook workbook) {
        Sheet sheet = workbook.createSheet("Assistidos");
        List<AssistidoEntity> assistidos = assistidoRepository.findAll();
        String[] colunas = {
                "ID",
                "Nome",
                "DataNascimento",
                "Sexo",
                "Escolaridade",
                "Escola",
                "TelEscola",
                "CadastroEmInstituição",
                "Instituição",
                "EncaminhadoPara",
                "QuemIndicouApajac",
                "InformacoesFornecidasPor",
                "ResponsavelPeloCadastro",
                "CadastradoEm",
                "DataAlteracaoStatus",
                "StatusAssistido",
                "Endereco",
                "Observações",
                "Familiares"
        };

        preencherDados(sheet, colunas, assistidos, assistido -> new String[]{
                assistido.getId().toString(),
                assistido.getNome(),
                assistido.getDataNascimento().toString(),
                assistido.getSexo().toString(),
                assistido.getEscolaridade(),
                assistido.getEscola(),
                assistido.getTelEscola(),
                String.valueOf(assistido.isCadastroInstituicao()),
                assistido.getInstituicao(),
                assistido.getEncaminhadoPara(),
                assistido.getQuemIndicouApajac(),
                assistido.getInformacoesFornecidasPor(),
                assistido.getIdResponsavelPeloCadastro() != null ? assistido.getIdResponsavelPeloCadastro().toString() : "",
                assistido.getCadastradoEm().toString(),
                assistido.getDataAlteracaoStatus() != null ? assistido.getDataAlteracaoStatus().toString() : "",
                String.valueOf(assistido.isStatusAssistido()),
                assistido.getEndereco() != null ?
                        assistido.getEndereco().getEndereco()
                                .concat(", " + assistido.getEndereco().getNumero()
                                .concat(", " + assistido.getEndereco().getBairro()
                                .concat(", " + assistido.getEndereco().getCidade()
                                .concat(", " + assistido.getEndereco().getCep()
                                                )))) : "",
                assistido.getObservacoes(),
                assistido.getFamiliares().stream()
                        .map(FamiliarEntity::getNome)
                        .collect(Collectors.joining(", ")),
        });
    }

//    private void criarAbaContatos(Workbook workbook) {
//        Sheet sheet = workbook.createSheet("Contatos");
//        List<ContatoEntity> contatos = contatoRepository.findAll();
//        String[] colunas = {"ID", "Nome", "Telefone", "Email"};
//
//        preencherDados(sheet, colunas, contatos, contato -> new String[]{
//                contato.getId().toString(),
//                contato.getContato(),
//                contato.getResponsavel().toString()
//        });
//    }

//    private void criarAbaFamiliares(Workbook workbook) {
//        Sheet sheet = workbook.createSheet("Familiares");
//        List<Familiar> familiares = familiarRepository.findAll();
//        String[] colunas = {"ID", "Nome", "Parentesco", "Contato"};
//
//        preencherDados(sheet, colunas, familiares, familiar -> new String[]{
//                familiar.getId().toString(),
//                familiar.getNome(),
//                familiar.getParentesco(),
//                familiar.getContato()
//        });
//    }
//
//    private void criarAbaResponsaveis(Workbook workbook) {
//        Sheet sheet = workbook.createSheet("Responsaveis");
//        List<Responsavel> responsaveis = responsavelRepository.findAll();
//        String[] colunas = {"ID", "Nome", "Telefone", "Endereço"};
//
//        preencherDados(sheet, colunas, responsaveis, responsavel -> new String[]{
//                responsavel.getId().toString(),
//                responsavel.getNome(),
//                responsavel.getTelefone(),
//                responsavel.getEndereco()
//        });
//    }

    private <T> void preencherDados(Sheet sheet, String[] colunas, List<T> dados, DataExtractor<T> extractor) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < colunas.length; i++) {
            headerRow.createCell(i).setCellValue(colunas[i]);
        }

        int rowNum = 1;
        for (T item : dados) {
            Row row = sheet.createRow(rowNum++);
            String[] valores = extractor.extractData(item);
            for (int i = 0; i < valores.length; i++) {
                row.createCell(i).setCellValue(valores[i]);
            }
        }
    }

    @FunctionalInterface
    private interface DataExtractor<T> {
        String[] extractData(T item);
    }

}
