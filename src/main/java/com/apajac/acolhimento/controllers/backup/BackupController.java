package com.apajac.acolhimento.controllers.backup;

import com.apajac.acolhimento.services.BackupService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/backup")
@Tag(name = "Backup", description = "Endpoint responsavel por gerar uma planilha com dados da Base.")
public class BackupController {

    private final BackupService backupService;

    @GetMapping("/excel")
    public ResponseEntity<byte[]> gerarBackupExcel(@RequestParam(required = false, defaultValue = "1234") String senha) {
        byte[] excelFile = backupService.gerarBackupExcelComSenha(senha);

        LocalDate dataAtual = LocalDate.now();
        String FILE_NAME = "attachment; filename=planilha_backup[" + dataAtual.getDayOfMonth() + "-" + dataAtual.getMonthValue() + "-" + dataAtual.getYear() + "].xlsx";

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, FILE_NAME);
        headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        return new ResponseEntity<>(excelFile, headers, HttpStatus.OK);
    }

}
