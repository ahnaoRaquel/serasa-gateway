package com.gateway.serasa.util;

import java.util.InputMismatchException;

public class ValidadorDocumento {

    public static boolean validarCPF(String cpf) {
        cpf = cpf.replaceAll("[^\\d]", ""); // remove caracteres não numéricos
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) return false;

        try {
            int sum1 = 0;
            int sum2 = 0;
            for (int i = 0; i < 9; i++) {
                int num = Character.getNumericValue(cpf.charAt(i));
                sum1 += num * (10 - i);
                sum2 += num * (11 - i);
            }

            int dig1 = 11 - (sum1 % 11);
            dig1 = (dig1 >= 10) ? 0 : dig1;

            sum2 += dig1 * 2;
            int dig2 = 11 - (sum2 % 11);
            dig2 = (dig2 >= 10) ? 0 : dig2;

            return dig1 == Character.getNumericValue(cpf.charAt(9)) &&
                    dig2 == Character.getNumericValue(cpf.charAt(10));
        } catch (InputMismatchException e) {
            return false;
        }
    }

    public static boolean validarCNPJ(String cnpj) {
        cnpj = cnpj.replaceAll("[^\\d]", "");
        if (cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}")) return false;

        try {
            int[] peso1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int[] peso2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

            int sum = 0;
            for (int i = 0; i < 12; i++) sum += Character.getNumericValue(cnpj.charAt(i)) * peso1[i];
            int dig1 = sum % 11;
            dig1 = (dig1 < 2) ? 0 : 11 - dig1;

            sum = 0;
            for (int i = 0; i < 13; i++) sum += Character.getNumericValue(cnpj.charAt(i)) * peso2[i];
            int dig2 = sum % 11;
            dig2 = (dig2 < 2) ? 0 : 11 - dig2;

            return dig1 == Character.getNumericValue(cnpj.charAt(12)) &&
                    dig2 == Character.getNumericValue(cnpj.charAt(13));
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean validarDocumento(String doc) {
        try {
            String documento = doc.replaceAll("\\D", "");
            if (documento.length() == 11) {
                return validarCPF(documento);
            } else if (documento.length() == 14) {
                return validarCNPJ(documento);
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

}