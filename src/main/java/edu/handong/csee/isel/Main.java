package edu.handong.csee.isel;

import edu.handong.csee.isel.data.CsvReader;
import edu.handong.csee.isel.model.Logistic;
import edu.handong.csee.isel.model.LinearRegression;

import org.apache.commons.cli.*;

public class Main {
    public static void main(String[] args) {
        Options options = new Options(); //Options 객체: 프로그램이 받을 수 있는 옵션을 등록할 수 있도록 함

        options.addOption(Option.builder("m") //짧은 옵션 이름 설정했을때
                .longOpt("model")           // --model 긴 옵션 이름으로 설정했을때
                .hasArg()                   // 값을 하나만 받는다는 선언
                .desc("모델 선택 (기본값: logistic)") //--help시에 보여줄 설명
                .build()); //Option 객체 완성

        options.addOption(Option.builder("e")
                .longOpt("epoch")           // --epoch
                .hasArg()
                .desc("학습 횟수 (기본값: 10)")
                .build());

        options.addOption(Option.builder("l")
                .longOpt("lr")              // --lr
                .hasArg()
                .desc("러닝레이트 (기본값: 0.001)")
                .build());

        options.addOption(Option.builder("f")
                .longOpt("file")            // --file
                .hasArg()
                .desc("CSV 파일명")
                .build());

        //.hasArg()없음: --help는 값 없다.
        options.addOption(Option.builder("h")
                .longOpt("help")            // --help
                .desc("도움말 출력")
                .build());

        // 파싱 (args[]를 읽어서 분석)
        CommandLineParser parser = new DefaultParser(); //args[]를 읽어서 옵션 이름과 값을 분리해주는 파서
        HelpFormatter helpFormatter = new HelpFormatter();//--help 요청 시 옵션 목록을 보기 좋게 출력해주는 객체
        CommandLine cmd;//파싱 결과를 담는 객체. 이후 값을 꺼낼 때 사용

        try {
            cmd = parser.parse(options, args);
            // parser.parse(options, args): args[]를 options에 정의된 규칙대로 파싱
            // 정의되지 않은 옵션(--abc 등)이 들어오면 자동으로 ParseException 발생
            // 기존 방식은 이걸 직접 체크했지만, Commons CLI는 자동으로 잡아줌
        } catch (ParseException e) {
            System.out.println("옵션 오류: " + e.getMessage());
            helpFormatter.printHelp("ITC_KYC", options);  // 사용법 자동 출력
            return;
        }

        // ── 3단계: 조회 (파싱된 값 가져오기) ──────────────────────


        if (cmd.hasOption("help")) { //--help 옵션이 입력됐는지 확인
            helpFormatter.printHelp("ITC_KYC", options); //사용법 출력 후 종료하기
            return;
        }

        // getOptionValue("옵션명", "기본값") 으로 기본값 설정 가능
        String model    = cmd.getOptionValue("model", "logistic");
        int epoch       = Integer.parseInt(cmd.getOptionValue("epoch", "10"));
        double lr       = Double.parseDouble(cmd.getOptionValue("lr", "0.001"));
        String fileName = cmd.getOptionValue("file",
                model.equals("linear") ? "linear_data.csv" : "logistic_data.csv");

        /*
        //args[]는 프로그램 실행 시 외부에서 전달되는 문자열 배열이다.
        // java -jar 자르파일경로.jar --edu.handong.csee.isel.model logistic --epoch 100
        //이렇게 실행하면 args안에 값들이 담긴다.

        //커맨드라인 옵션 처리 하는 이유: 코드를 수정하지 않고, 파라미터값들 바꿀 수 있다.
        //재빌드 없이 실행 인자만 바꾸기가 가능하다.

        // 기본값 설정 (옵션 선택 안했을때)
        String model = "logistic";
        int epoch = 10;
        double lr = 0.001;
        String fileName = null; // 기본값은 모델에 따라 자동 결정

        // args[] 파싱
        // args배열을 순서대로 보면서 옵션 네임, 값 파싱하기
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) { // 옵션 네임 확인하고
                case "--model":
                    model = args[++i]; //전위 연산자로 바로 다음에 들어있는 값 가져오기
                    break;
                case "--epoch":
                    epoch = Integer.parseInt(args[++i]);
                    break;
                case "--lr":
                    lr = Double.parseDouble(args[++i]);
                    break;
                case "--file":
                    fileName = args[++i];
                    break;
                default:
                    System.out.println("알 수 없는 옵션: " + args[i]);
            }
        }

        // 파일명 기본값: 모델에 따라 자동 결정
        if (fileName == null) {
            fileName = model.equals("linear") ? "linear_data.csv" : "logistic_data.csv";
        }

        */
        System.out.println("=== 설정 ===");
        System.out.println("모델     : " + model);
        System.out.println("epoch    : " + epoch);
        System.out.println("lr       : " + lr);
        System.out.println("파일     : " + fileName);
        System.out.println("============");

        CsvReader reader = new CsvReader();
        double[][] data = reader.readCsv(fileName);


        if (model.equals("logistic")) {
            Logistic logisticModel = new Logistic(epoch, lr);
            logisticModel.training(data);

        } else if (model.equals("linear")) {
            LinearRegression linearModel = new LinearRegression(epoch, lr);
            linearModel.training(data);

        } else {
            System.out.println("오류: 알 수 없는 모델 '" + model + "' (logistic 또는 linear 사용)");
        }
    }
}