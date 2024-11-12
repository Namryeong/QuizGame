# QuizGame
Java socket을 이용한 컴퓨터 네트워크 Quiz Game

![image](https://github.com/user-attachments/assets/5547306a-7b6c-4150-823d-43f183f6828e)

# Protocol 설명
•	1. Server 구성
1.	QuizServer는 server_info.dat 파일에서 IP와 Port 번호를 읽는다.
2.	만약 server_info.dat 파일이 존재하지 않으면 IP를 기본값인 localhost, Port 번호를 1234로 설정한다.
3.	ServerSocket을 생성하여 Client의 연결 요청을 기다린다.
4.	ExecuterService로 20개의 스레드를 생성함으로써 Multi-client Support를 가능하게 한다.
•	2. Client 구성
1.	QuizClient는 server_info.dat 파일에서 IP와 Port 번호를 읽는다.
2.	만약 server_info.dat 파일이 존재하지 않으면 IP를 기본값인 localhost, Port 번호를 1234로 설정한다.
3.	Socket을 생성하여 서버에 연결한다.
•	3. Quiz Protocol
1.	Server가 Client에게 quiz_questions 배열의 질문을 전송한다.
2.	Client는 Server로부터 받은 문제를 출력한 후 사용자의 입력을 받아 입력을 Server로 전송한다.
3.	Server가 Client에게 받은 정답을 quiz_answers 배열의 정답과 비교한 뒤, 정답인 경우 “Correct!”, 오답일 경우 “Incorrect” 메시지를 Client에게 보낸다.
4.	만약 정답일 경우 score에 20점을 더한다.
5.	위 절차를 quiz_questions 배열이 끝날 때까지 반복한다.
6.	모든 문제를 전송하고 응답을 받으면 Server는 Client에게 Total score를 전송한다.
7.	Client는 Total score 메시지를 받으면 연결을 종료한다.
