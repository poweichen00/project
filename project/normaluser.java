import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public abstract class normaluser extends user{
	private ArrayList<userbook> userbooks = new ArrayList<userbook>();
	private ArrayList<userbook> history = new ArrayList<userbook>();
	private int lendup;
	private int money;
	public normaluser(String n, String acc, String pass, String ident){
		super(n, acc, pass, ident);
		if(ident.equals("�ǥ�")) {
			this.lendup = 10;
		}
		else if(ident.equals("�Э�") || ident.equals("¾��")) {
			this.lendup = 15;
		}
	}
	public void action(user u) {
		String login[] = { "�n�X", "�d�ߩҦ��ɾ\���", "�j�M���y", "�d�߱Ʀ�]", "ú�ǻ@��" };
		int select;
		do {
			select = JOptionPane.showOptionDialog(null, u.getidentification() + u.getname() + "�z�n�G", "�@��ϥΪ̤���",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, login, null);
			switch (select) {
				case 1:
					watchinfo();
					break;
				case 2:
					addbook(u);
					break;
				case 3:
					ranking(u);
					break;
				case 4:
					payment();
			}
		} while (select != 0 && select != -1);

		if (select == -1) { //�����W���e�e���N��
			JOptionPane.showMessageDialog(null, "�Э��s�ާ@","�n�J�t��",1);
		}
	}

	public void watchinfo() {
			JFrame jf = new JFrame();
			jf.setSize(500, 500);
			jf.setLocationRelativeTo(null);
			JPanel myPanel = new JPanel();
			GridLayout experimentLayout = new GridLayout(0, ��) ;
			myPanel.setLayout(experimentLayout);
			myPanel.add(new JLabel());
			if(userbooks.size() > 0) {
				myPanel.add(new JLabel("�ѦW�@�@�@" + "���A"));
				for (userbook ub : userbooks) {
					myPanel.add(new JLabel(ub.getname() + "�@�@"
							+ (ub.getsituation() == "reserved" ? "�w����" : "�ɾ\��(��" + ub.getretrievedate() + ")")));
				}
			}
			else {
				myPanel.add(new JLabel("�ثe�L�ɾ\���y"));
			}
			
			myPanel.add(new JLabel());
			if(history.size() > 0) {
				myPanel.add(new JLabel("���v�ɾ\����"));
				myPanel.add(new JLabel("�ѦW�@�@�@" + "���A"));
				for (userbook ub : history) {
					myPanel.add(new JLabel(ub.getname() + "�@�@" + "�w�k��(��" + ub.getretrievedate() + ")"));
				}
			}
			else {
				myPanel.add(new JLabel("�ثe�L�ɾ\����"));
			}
			myPanel.add(new JLabel());
			myPanel.add(new JLabel((getmoney() == 0? "�ثe�L��ú�ǻ@��":"�ثe��ú�ǻ@���G	" + getmoney())));
			final JComboBox<String> comboBox = new JComboBox<String>();
			JLabel label = new JLabel("�п���k�٩έn�����w�������y�G");
			myPanel.add(label);
			for (userbook ub : userbooks) {
				comboBox.addItem(ub.getname());
			}
			if(userbooks.size() > 0) {
				comboBox.setSelectedIndex(0);
			}
			myPanel.add(comboBox);
			String retrieve_list[] = { "�k�٩Ψ����w��", "�h�X" };
			int result = JOptionPane.showOptionDialog(null, myPanel, "�Ҧ��ɾ\���", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, retrieve_list, null);
			if (result == 0) {
				for (userbook ub : userbooks) {
					if (ub.getname() == comboBox.getSelectedItem().toString()) {
						if (ub.getsituation() == "reserved") {
							cancel_reserved(ub);
							JOptionPane.showMessageDialog(null, "�����w���I�I","�ɾ\�t��",1);
						} else {
							removebook(new book("","",""), ub);
						}
						break;
					}
				}
			}
	}
	public void payment() {
		int i = Integer.parseInt(JOptionPane.showInputDialog(null, "�п�J��ú�ǻ@��","�@��ú�Ǩt�Ψt��",1));
		if(money > i) {
			money -= i;
		}
		else {
			JOptionPane.showMessageDialog(null, "��X�W�Lú�Ǫ��B�A�Э��s��J","�ٮѨt��",1);
			payment();
		}
	}
	public void removebook(book b, userbook ub) {
		adminstrator admin = new adminstrator("","","","");
		admin.user_retrieve(ub.getname());
		userbooks.remove(ub);
		history.add(ub);
		Date now = new Date();
		JOptionPane.showMessageDialog(null,
				"�k�٦��\�I�I\n" + (money_amount(ub, now) == 0 ? "�L�s�W�@��" : "�s�W�@��" + money_amount(ub, now)),"�ٮѨt��",1);
		watchinfo();
	}

	public void addbook(user u) {
		userbook_manage ubm = new userbook_manage();
		adminstrator admin = new adminstrator("","","","");
		book adbook = new book("", "", "");
		search_books = admin.new_search_gui();
		JFrame jf = new JFrame();
		jf.setSize(500, 500);
		jf.setLocationRelativeTo(null);
		JPanel myPanel = new JPanel();
		GridLayout experimentLayout = new GridLayout(0, 1);
		myPanel.setLayout(experimentLayout);
		myPanel.add(new JLabel("�ѦW�@�@�@�X�����@�@�@�@�̡@�@�@�@���A\n"));

		if (search_books.size() != 0) {
			for (book b : search_books) {
				myPanel.add(new JLabel(b.getname() + "�@�@�@" + b.getpublish() + "�@�@�@" + b.getauthor() + "�@�@�@"
						+ (b.getsituation() == "available" ? "�i�ɾ\" : "�w�Q�ɾ\(��" + ubm.retrieve_date(b.getname()) + ")")));
			}
			final JComboBox<String> comboBox = new JComboBox<String>();
			JLabel label = new JLabel("�п�ܭn�ɾ\�����y");
			myPanel.add(label);

			for (book b : search_books) {
				comboBox.addItem(b.getname());
			}
			comboBox.setSelectedIndex(0);
			myPanel.add(comboBox);
			String borrow_list[] = { "�ɾ\", "�h�X" };
			int result = JOptionPane.showOptionDialog(null, myPanel, "�j�M�P�ɾ\����", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, borrow_list, null);
			adbook = admin.borrow_book_search(comboBox.getSelectedItem().toString());
			if (result == 0) {
				borrow_book_support(adbook, u);
			}
		} else {
			JOptionPane.showMessageDialog(null, "�d�L�����y","�j�M�P�ɾ\�t��",1);
		}
	}

	public void cancel_reserved(userbook ub) {
		userbooks.remove(ub);
	}

	public int money_amount(userbook ub, Date now) {
		long diff = now.getTime() - ub.getretrievedate2().getTime();
		if (diff < 0) {
			diff = 0;
		}
		TimeUnit time = TimeUnit.DAYS;
		long difference = time.convert(diff, TimeUnit.MILLISECONDS);
		int temp_money = (int) (difference) * 100;
		setmoney(temp_money);
		return temp_money;
	}

	public String getname() {return super.getname();}
	public int getlendup() {return lendup;}
	public int getmoney() {return money;}
	public void setmoney(int m) {this.money += m;}

	public String support_retrieve_date(String bkname) {
		for (userbook ub : userbooks) {
			if (ub.getname() == bkname) {
				return ub.getretrievedate();
			}
		}
		return "0";
	}
	public void borrow_book_support(book adbook, user u ) {
		userbook_manage ubm = new userbook_manage();
		if (userbooks.size() >= lendup) {
			JOptionPane.showMessageDialog(null, "�W�L�ɾ\�ƶq" ,"�j�M�t��",1);
		} else {
			String response = "";
			if (adbook.getsituation() == "unavailable") {
				int you_borrow = 0;
				for (userbook ub : userbooks) {
					if (ub.getname() == adbook.getname()) {
						you_borrow = 1;
					}
				}
				if (you_borrow == 1) {
					response += "�z�w�ɾ\, ���i���ƭɾ\" + ubm.retrieve_date(adbook.getname());
					JOptionPane.showMessageDialog(null, response,"�j�M�P�ɾ\�t��",1);
				} else {
					response += "\n���Ѥw�Q�O�H�ɾ\��" + ubm.retrieve_date(adbook.getname());
					JOptionPane.showMessageDialog(null, response,"�j�M�P�ɾ\�t��",1);
					String reserve[] = { "�w��", "�h�X" };
					int select = JOptionPane.showOptionDialog(null, "�z�n�w���ܡH", null + "�ǥ�",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, reserve, null);
					if (select == 0) {
						userbook ub = new userbook(adbook.getname(), adbook.getpublish(), adbook.getauthor(),
								"reserved");
						userbooks.add(ub);
					}
				}
			} else {
				userbook ub = new userbook(adbook.getname(), adbook.getpublish(), adbook.getauthor());
				JOptionPane.showMessageDialog(null, "�ɾ\��" + ub.getretrievedate(),"�j�M�P�ɾ\�t��",1);
				userbooks.add(ub);
				adbook.setsituation("unavailable");
				adbook.setcount();
			}
		}
	}
	public void ranking(user u) {
		adminstrator admin = new adminstrator("","","","");
		userbook_manage ubm = new userbook_manage();
		String[] t = admin.top3().split(",");

		if (4 > t.length && t.length > 0) {
			JFrame jf = new JFrame();
			jf.setSize(500, 500);
			jf.setLocationRelativeTo(null);
			JPanel myPanel = new JPanel();
			GridLayout experimentLayout = new GridLayout(0, 1);
			myPanel.setLayout(experimentLayout);
			myPanel.add(new JLabel("�Ʀ�@�@�@�ѦW�@�@�@����\n"));
			for (int i = 0; i < t.length; i++) {
				int amount = admin.borrow_book_search(t[i]).getcount();
				myPanel.add(new JLabel(i + 1 + "�@�@�@" + t[i] + "�@�@�@" + amount));
			}
			final JComboBox<String> comboBox = new JComboBox<String>();
			JLabel label = new JLabel("�п�ܭn�ɾ\�����y�G");
			myPanel.add(label);
			for (String s : t) {
				comboBox.addItem(s);
			}
			comboBox.setSelectedIndex(0);
			myPanel.add(comboBox);
			String borrow_list[] = { "�ɾ\", "�h�X" };
			int result = JOptionPane.showOptionDialog(null, myPanel, "�ɾ\", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, borrow_list, null);
			book adbook = new book("", "", "");
			adbook = admin.borrow_book_search(comboBox.getSelectedItem().toString());
			if (result == 0) {
				borrow_book_support(adbook, u);
			}
		}
	}

}