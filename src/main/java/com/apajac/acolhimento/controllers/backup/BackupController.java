package com.apajac.acolhimento.controllers.backup;

import com.apajac.acolhimento.services.BackupService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/backup")
public class BackupController {

    private final BackupService backupService;

    public BackupController(BackupService backupService) {
        this.backupService = backupService;
    }

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
