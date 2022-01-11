package manage;

import model.Contact;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactManage {
    public final static String PATH_CONTACT = "src/data/contact.csv";
    ArrayList<Contact> contacts = readCSV();
    Scanner sc = new Scanner(System.in);

    public ContactManage() {
    }

    public ArrayList<Contact> displayContactList() {
        return contacts;
    }

    public Contact creatContact() {
        System.out.println("Nhập số điện thoại (9 hoặc 10 số) : ");
        String phoneNumber = sc.nextLine();
        if (checkPhoneNumber(phoneNumber)) {
            System.out.println("Số điện thoại đã được lưu trong danh bạ");
            return null;
        }
        System.out.println("Nhập nhóm : ");
        String group = sc.nextLine();
        System.out.println("Họ và tên : ");
        String name = sc.nextLine();
        System.out.println("Giới tính : ");
        String gender = sc.nextLine();
        System.out.println("Địa chỉ : ");
        String address = sc.nextLine();
        System.out.println("Ngày sinh : ");
        String dateOfBirth = sc.nextLine();
        System.out.println("Email : ");
        String email = sc.nextLine();
        if (checkRegexPhoneNumber(phoneNumber) && checkRegexEmail(email)) {
            return new Contact(phoneNumber, group, name, gender, address, dateOfBirth, email);
        } else {
            System.out.println("Lỗi số điện thoại hoặc email");
            return null;
        }
    }

    public void addContact() {
        Contact contact = creatContact();
        if (contact != null) {
            contacts.add(contact);
            writeCSV(contacts);
            System.out.println("Thêm vào danh bạ thành công!");

        } else {
            System.out.println("Thêm vào danh bạ không thành công!");
        }
    }

    public void updateContact(String phoneNumber) {
        if (checkPhoneNumber(phoneNumber)) {
            System.out.println("Nhập nhóm mới: ");
            String group = sc.nextLine();
            System.out.println("Họ và tên mới: ");
            String name = sc.nextLine();
            System.out.println("Giới tính mới: ");
            String gender = sc.nextLine();
            System.out.println("Địa chỉ mới: ");
            String address = sc.nextLine();
            System.out.println("Ngày sinh mới: ");
            String dateOfBirth = sc.nextLine();
            System.out.println("Email mới: ");
            for (Contact contact : contacts) {
                if (contact.getPhoneNumber().equals(phoneNumber)) {
                    contact.setGroup(group);
                    contact.setName(name);
                    contact.setGender(gender);
                    contact.setAddress(address);
                    contact.setDateOfBirth(dateOfBirth);
                }
            }
            writeCSV(contacts);
            System.out.println("Cập nhật thành công!");
        } else {
            System.out.println("Không tìm được danh bạ với số điện thoại trên.");
        }
    }

    public void deleteContact(String phoneNumber) {
        if (checkPhoneNumber(phoneNumber)) {
            for (Contact contact : contacts) {
                if (contact.getPhoneNumber().equals(phoneNumber)) {
                    System.out.println("Bạn có muốn xóa (Y)");
                    String select = sc.nextLine();
                    if (select.equalsIgnoreCase("Y")) {
                        contacts.remove(contact);
                        writeCSV(contacts);
                        System.out.println("Xóa thành công!");
                    } else {
                        System.out.println("Không xóa số này!");
                    }
                }
            }
        } else { // Check lại, thêm điều kiện
            System.out.println("Không tìm được danh bạ với số điện thoại trên.");
        }
    }

    public ArrayList<Contact> findContact(String findInput) {
        ArrayList<Contact> contactsList = new ArrayList<>();
        System.out.println("Bạn muốn tìm theo số hay theo nhóm? (1 để tìm theo số, 2 để tìm theo nhóm)");
        int choice = sc.nextInt();
        sc.nextLine();
        String regex = ".*" + findInput + ".*";
        Pattern pattern = Pattern.compile(findInput);
        Matcher matcher;
        if (choice == 1) {
            for (Contact contact : contacts) {
                matcher = pattern.matcher(contact.getPhoneNumber());
                if (matcher.find()) {
                    contactsList.add(contact);
                }
            }
        } else if (choice == 2) {
            for (Contact contact : contacts) {
                matcher = pattern.matcher(contact.getGroup());
                if (matcher.find()) {
                    contactsList.add(contact);
                }
            }
        }
        return contactsList;
    }

    public void writeCSV(ArrayList<Contact> contactsList) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(PATH_CONTACT));
            for (Contact contact : contactsList) {
                bufferedWriter.write(contact.getPhoneNumber() + "," + contact.getGroup() + "," + contact.getName() + "," +
                        contact.getGender() + "," + contact.getAddress() + "," + contact.getDateOfBirth() + "," + contact.getEmail());
                bufferedWriter.write("\n");
            }
            bufferedWriter.close();
            System.out.println("Ghi thành công!");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public ArrayList<Contact> readCSV() {
        ArrayList<Contact> contactsList = new ArrayList<>();
        File file = new File(PATH_CONTACT);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    String[] output = line.split(",");
                    contactsList.add(new Contact(output[0], output[1], output[2], output[3], output[4], output[5], output[6]));
                }
//                System.out.println("Đọc thành công!");
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return contactsList;
    }


    public boolean checkPhoneNumber(String phoneNumber) {
        for (Contact contact : contacts) {
            if (contact.getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkRegexPhoneNumber(String phoneNumber) {
        String regex = "(([^0]\\d{8})$)|((0\\d{9})$)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.find();
    }

    ;

    public boolean checkRegexEmail(String email) {
        String regex = "^[a-zA-Z0-9]+@[a-z]+\\.(com|vn)+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }
}
