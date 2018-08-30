package com.deis.test.tasks;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by denis on 27.08.18.
 */
public class Main {
    public static void main(String[] args) {
        new Main().run(args);
    }

    private void run(String[] args) {
        String inputXmlPath = args[0];
        String inputXsdPath = args[1];
        List<Double> calculationResult = new ArrayList<>();
        System.out.println("Start");
        try {
            if (!XmlUtils.isXmlValid(inputXmlPath, inputXsdPath)) {
                System.out.println("Input XML has incorrect schema");
            }
            List<Operation> operations = XmlUtils.parseXml(inputXmlPath);
            Calculator calculator = new Calculator();

            for (Operation operator : operations) {
                calculationResult.add(Double.valueOf(calculator.calculate(operator)));
            }

            File outputXml = XmlUtils.createXml(calculationResult);
            if (!XmlUtils.isXmlValid(outputXml.getAbsolutePath(), inputXsdPath)) {
                System.out.println("Output XML has incorrect schema");
            }

            System.out.println("End");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
