package login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gui.RoundTextField;
import gui.RoundPasswordField;
import gui.RoundButton;
import java.util.regex.Pattern;

public class JoinPanel extends JPanel implements ActionListener {

    private JTextField userIdField, userEmailField, userNameField, userPhoneField, verificationCodeField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton checkIdButton, requestCodeButton, joinButton, backButton;
    private MainFrame mainFrame;
    private JLabel idErrLbl, PwErrLbl, PwCkErrLbl ; // 아이디 형식 오류 메시지

    public JoinPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setBackground(Color.WHITE);
        setLayout(null);

        // 🔹 폰트 설정
        Font interBold32 = new Font("Inter", Font.BOLD, 32);
        Font interRegular15 = new Font("Inter", Font.PLAIN, 15);
        Font interBold14 = new Font("Inter", Font.BOLD, 14);
        Font interRed14 = new Font("Inter", Font.PLAIN, 14);
        Font buttonFontLarge = new Font("Inter", Font.BOLD, 20);

        // 🔹 상단 제목
        JLabel titleLabel = new JLabel("회원가입");
        titleLabel.setFont(interBold32);
        titleLabel.setBounds(21, 8, 181, 58);
        add(titleLabel);

        // 🔹 구분선 (얇은 검은 테두리)
        JSeparator separator = new JSeparator();
        separator.setBounds(-18, 70, 458, 1);
        separator.setForeground(Color.BLACK);
        add(separator);

        //뒤로가기
        backButton = new JButton("<");
        backButton.setBounds(360, 20, 60, 60);
        backButton.addActionListener(this);
        backButton.setBorderPainted(false);  
        backButton.setContentAreaFilled(false);  
        backButton.setFocusPainted(false);  
        add(backButton);
        
        // 🔹 아이디
        // 입력 필드
        JLabel idLabel = new JLabel("아이디");
        idLabel.setFont(interRegular15);
        idLabel.setBounds(32, 164, 56, 21);
        add(idLabel);
        userIdField = createStyledTextField();
        userIdField.setBounds(32, 185, 281, 41);
        add(userIdField);
        // 중복확인 버튼
        checkIdButton = createStyledButton("ID 중복확인", interBold14);
        checkIdButton.setBounds(324, 185, 84, 41);
        checkIdButton.addActionListener(this);
        add(checkIdButton);
        // 형식 오류 메시지 (초기에는 보이지 않도록 설정)
        idErrLbl = new JLabel();
        idErrLbl.setFont(new Font("Inter", Font.PLAIN, 10));
        idErrLbl.setForeground(Color.RED);
        idErrLbl.setVisible(false); // 기본적으로 보이지 않도록 설정
        add(idErrLbl);
        // 실시간으로 형식 검사 (KeyListener 적용)
        userIdField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String userId = userIdField.getText().trim();
                idErrLbl.setBounds(32, 230, 350, 20); // 아이디 입력란 아래에 위치
                if (userId.isEmpty()) {
                	idErrLbl.setText("아이디를 입력하세요.");
                	idErrLbl.setVisible(true);
                } else if (!isValidUserId(userId)) {
                	idErrLbl.setText("아이디는 6~20자 사이의 영문(대소문자), 특수문자, 숫자로 만들수 있습니다.");
                	idErrLbl.setVisible(true);
                } else {
                	idErrLbl.setVisible(false);
                }
            }
        });
        
        // 🔹 비밀번호
        // 라벨
        JLabel passwordLabel = new JLabel("비밀번호");
        passwordLabel.setFont(interRegular15);
        passwordLabel.setBounds(32, 250, 80, 21); // 🔹 20px 아래로 조정
        add(passwordLabel);
        // 입력 필드
        passwordField = createStyledPasswordField();
        passwordField.setBounds(32, 271, 376, 41); // 🔹 20px 아래로 조정
        add(passwordField);
        // 형식 오류 메시지 (초기에는 보이지 않도록 설정)
        PwErrLbl = new JLabel();
        PwErrLbl.setFont(new Font("Inter", Font.PLAIN, 11));
        PwErrLbl.setForeground(Color.RED);
        PwErrLbl.setVisible(false); // 기본적으로 보이지 않도록 설정
        add(PwErrLbl);
        // 실시간으로 형식 검사 (KeyListener 적용)
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String userpw = passwordField.getText().trim();
                PwErrLbl.setBounds(32, 316, 350, 20);
                if (userpw.isEmpty()) {
                	PwErrLbl.setText("비밀번호를 입력하세요.");
                	PwErrLbl.setVisible(true);
                } else if (!isCreateUserPw(userpw)) {
                	PwErrLbl.setText("비밀번호는 영문(대소문자) + 특수문자를 포함한 6~20자여야 합니다.");
                	PwErrLbl.setVisible(true);
                } else {
                	PwErrLbl.setVisible(false);
                }
            }
        }); //-- passwordField.addKeyListener
   
        
        // 🔹비밀번호 확인
        // 확인라벨
        JLabel confirmPasswordLabel = new JLabel("비밀번호 확인");
        confirmPasswordLabel.setFont(interRegular15);
        confirmPasswordLabel.setBounds(32, 336, 150, 21); // 🔹 20px 아래로 조정
        add(confirmPasswordLabel);
        // 형식 오류 메시지 (초기에는 보이지 않도록 설정)
        PwCkErrLbl = new JLabel();
        PwCkErrLbl.setFont(new Font("Inter", Font.PLAIN, 11));
        PwCkErrLbl.setForeground(Color.RED);
        PwCkErrLbl.setBounds(32, 230, 350, 20); // 아이디 입력란 아래에 위치
        PwCkErrLbl.setVisible(false); // 기본적으로 보이지 않도록 설정
        add(PwCkErrLbl);
        // 확인 필드
        confirmPasswordField = createStyledPasswordField();
        confirmPasswordField.setBounds(32, 357, 376, 41); // 🔹 20px 아래로 조정
        add(confirmPasswordField);
        confirmPasswordField.addKeyListener(new KeyAdapter() {
        	@Override
            public void keyReleased(KeyEvent e) {
        		String userPw = new String(passwordField.getPassword()).trim();
                String confirmPw = new String(confirmPasswordField.getPassword()).trim();
                
                PwCkErrLbl.setBounds(32, 402, 350, 20);
                if (confirmPw.isEmpty()) {
                    PwCkErrLbl.setText("비밀번호 확인을 입력하세요.");
                    PwCkErrLbl.setVisible(true);
                } else if (!userPw.equals(confirmPw)) {
                    PwCkErrLbl.setText("비밀번호가 일치하지 않습니다.");
                    PwCkErrLbl.setVisible(true);
                } else {
                    PwCkErrLbl.setVisible(false); // ✅ 비밀번호가 일치하면 경고 메시지 숨김
                }
            }
		}); // -- confirmPasswordField.addKeyListener
        

        // 🔹 이름 
        //입력 라벨
        JLabel nameLabel = new JLabel("이름");
        nameLabel.setFont(interRegular15);
        nameLabel.setBounds(32, 422, 400, 21); // 🔹 20px 아래로 조정
        add(nameLabel);
        //입력 필드
        userNameField = createStyledTextField();
        userNameField.setBounds(32, 443, 376, 41); // 🔹 20px 아래로 조정
        add(userNameField);

        
        // 🔹 이메일 
        // 입력 라벨
        JLabel emailLabel = new JLabel("이메일");
        emailLabel.setFont(interRegular15);
        emailLabel.setBounds(32, 508, 80, 21); // 🔹 20px 아래로 조정
        add(emailLabel);
        // 입력 필드
        userEmailField = createStyledTextField();
        userEmailField.setBounds(32, 529, 376, 41); // 🔹 20px 아래로 조정
        add(userEmailField);
		
        
        // 🔹 휴대폰 
        // 입력 라벨
        JLabel phoneLabel = new JLabel("휴대폰 번호");
        phoneLabel.setFont(interRegular15);
        phoneLabel.setBounds(32, 594, 94, 21); // 🔹 20px 아래로 조정
        add(phoneLabel);
        //번호 필드
        userPhoneField = createStyledTextField();
        userPhoneField.setBounds(32, 615, 270, 40); // 🔹 20px 아래로 조정
        add(userPhoneField);
        // 인증번호 버튼
        requestCodeButton = createStyledButton("인증번호받기", interBold14);
        requestCodeButton.setBounds(312, 615, 96, 40); // 🔹 20px 아래로 조정
        requestCodeButton.addActionListener(this);
        add(requestCodeButton);

        // 🔹 인증번호 입력 필드
        /*
        verificationCodeField = createStyledTextField();
        verificationCodeField.setBounds(156, 653, 146, 37); // 🔹 20px 아래로 조정
        add(verificationCodeField);
		*/
        
        // 🔹 필수 입력 안내
        JLabel requiredLabel = new JLabel("*는 필수 입력");
        requiredLabel.setFont(interRed14);
        requiredLabel.setBounds(283, 654, 125, 27); // 🔹 20px 아래로 조정
        requiredLabel.setForeground(Color.RED);
        add(requiredLabel);

        // 🔹 회원가입 버튼 (큰 글씨 적용)
        joinButton = createStyledButton("회원가입", buttonFontLarge);
        joinButton.setBounds(127, 750, 187, 52); // 🔹 20px 아래로 조정
        joinButton.addActionListener(this);
        add(joinButton);
    }

 // 회원가입 버튼 클릭 이벤트 처리
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == joinButton) {
            // 🔹 입력값 가져오기
            String userId = userIdField.getText();
            String userPwd = new String(passwordField.getPassword());
            String confirmPwd = new String(confirmPasswordField.getPassword());
            String userName = userNameField.getText();
            String userPhone = userPhoneField.getText();
            String userEmail = userEmailField.getText();

            // 🔹 필수 입력 검증
            if (userId.isEmpty() || userPwd.isEmpty() || confirmPwd.isEmpty() || 
                userName.isEmpty() || userPhone.isEmpty() || userEmail.isEmpty()) {
                CustomDialog.showDialog(mainFrame, "모든 필수 입력 사항을 입력해주세요.", "회원가입 오류");
                return;
            }
 
            // 🔹 회원 객체 생성
            LoginBean user = new LoginBean();
            user.setUser_id(userId);
            user.setUser_pwd(userPwd);
            user.setUser_name(userName);
            user.setUser_phone(userPhone);
            user.setUser_email(userEmail);

            // 🔹 회원가입 처리
            JoinDAO joinDAO = new JoinDAO();
            boolean isJoin = joinDAO.joinUser(user);

            if (isJoin) {
                CustomDialog.showDialog(mainFrame, userId + "님 환영합니다! <br> 회원가입이 완료되었습니다!", "회원가입 완료");
                mainFrame.showPanel("login"); // 로그인 화면으로 이동
            } else {
                CustomDialog.showDialog(mainFrame, "회원가입에 실패했습니다. 다시 시도해주세요.", "회원가입 오류");
            }
        } //-- if  회원가입 버튼 작동
        
        // 🔹 아이디 중복확인
        else if (e.getSource() == checkIdButton) {
        	String userId = userIdField.getText().trim();
            
        	
            try {
            	 JoinDAO joinDAO = new JoinDAO();
                boolean idCheck = joinDAO.isUserIdExists(userId);
                if (idCheck) {
                    JOptionPane.showMessageDialog(this, "이미 사용 중인 아이디입니다.", "알림", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "사용 가능한 아이디입니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "오류 발생: " + ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
            }
        } //--else if checkIdButton
        
        else if(e.getSource() == backButton) {
        	mainFrame.showPanel("login");
        }
       
    } //-- actionPerformed

    // 🔹 스타일 적용된 텍스트 필드 생성
    private JTextField createStyledTextField() {
    	JTextField textField = new RoundTextField(20) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.BLACK); // 테두리 색상
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
            }
        };
        return textField;
    }

    // 🔹 스타일 적용된 비밀번호 필드 생성
    private JPasswordField createStyledPasswordField() {
    	JPasswordField passwordField = new RoundPasswordField(20) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.BLACK); // 테두리 색상
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
            }
        };
        return passwordField;
    }

    // 🔹 스타일 적용된 버튼 생성
    private JButton createStyledButton(String text, Font font) {
        JButton button = new RoundButton(text, Color.BLACK, Color.WHITE);
        button.setFont(font); // 버튼 폰트 크기 조정
        return button;
    }
    
    // 아이디 양식 검사
    private boolean isValidUserId(String userId) {
        // 영문 대소문자, 숫자, 특수문자 중 하나 이상 포함된 6~20자의 아이디
        return Pattern.matches("^[A-Za-z0-9@#$%^&+=*!?]{6,20}$", userId);
    }
    
    //비밀번호 양식검사
    private boolean isCreateUserPw(String userId) {
        // 영문 대소문자와 특수문자가 반드시 포함된 6~20자의 아이디
        return Pattern.matches("^(?=.*[A-Za-z])(?=.*[@#$%^&+=*!?])[A-Za-z0-9@#$%^&+=*!?]{6,20}$", userId);
    }
    
 
} // --End
