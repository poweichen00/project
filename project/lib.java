import javax.swing.JOptionPane;
import java.util.ArrayList;

public class lib{
	public static void main(String[] args) {
		account_manage ac = new account_manage();
		adminstrator admin = new adminstrator("","","","");
		ac.initialize_user();
		admin.initialize_book();
		menu();
	}

	public static void menu() {
		String login[] = { "���U", "�n�J", "�X��" };
		int select;
		account_manage ac = new account_manage();
		do {
			select = JOptionPane.showOptionDialog(null, "�w��Ө줤�j�ɮѨt�ΡI", "���j�ɾ\�t��", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, login, 1);
			if (select == 0) {
				ac.regis();
			}

			if (select == 1) {
				user u = ac.login();
				userbook_manage ubm = new userbook_manage();
				if (u.getidentification() == "�Э�") {
					teacher tea = new teacher(u.getname(), u.getaccount(), u.getpassword(), u.getidentification());
					ubm.addtea(tea);
					tea.action(u);

				} else if (u.getidentification().equals("�ǥ�")) {
					student stu = new student(u.getname(), u.getaccount(), u.getpassword(), u.getidentification());
					ubm.addstu(stu);
					stu.action(u);

				} else if (u.getidentification().equals("¾��")) {
					staff sta = new staff(u.getname(), u.getaccount(), u.getpassword(), u.getidentification());
					ubm.addsta(sta);
					sta.action(u);

				} else if (u.getidentification().equals("�޲z��")) {
					adminstrator temp_bkm = new adminstrator(u.getname(), u.getaccount(), u.getpassword(),
							u.getidentification());
					temp_bkm.action(u);
				}
			} else if (select == 2) {
				visitor v = new visitor();
				v.action();
			}
		} while (select == 0 || select == 1 || select == 2 || select == 3);
		JOptionPane.showMessageDialog(null, "���¥��{","���j�ϮѨt��",1);
	}

}