package main;

import manage.ContactManage;
import model.Contact;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ContactManage contactManage = new ContactManage();
        int choice;
        boolean check = true;
        while (check) {
            try {
                do {
                    System.out.println("----CHƯƠNG TRÌNH QUẢN LÝ DANH BẠ----");
                    System.out.println("Chọn chức năng theo số (để tiếp tục): ");
                    System.out.println("1. Xem danh sách");
                    System.out.println("2. Thêm mới");
                    System.out.println("3. Cập nhật");
                    System.out.println("4. Xóa");
                    System.out.println("5. Tìm kiếm");
                    System.out.println("6. Đọc từ file");
                    System.out.println("7. Ghi từ file");
                    System.out.println("8. Thoát");
                    System.out.print("Chọn chức năng : ");
                    choice = Integer.parseInt(sc.nextLine());
                    switch (choice) {
                        case 1:
                            if (!contactManage.displayContactList().isEmpty()) {
                                for (Contact contact : contactManage.displayContactList()) {
                                    System.out.println(contact);
                                }
                            } else {
                                System.out.println("Danh bạ trống");
                            }
                            break;
                        case 2:
                            contactManage.addContact();
                            break;
                        case 3:
                            System.out.println("Nhập số điện thoại cần cập nhật: ");
                            String phoneNumberUpdate = sc.nextLine();
                            contactManage.updateContact(phoneNumberUpdate);
                            break;
                        case 4:
                            System.out.println("Nhập số điện thoại cần xóa: ");
                            String phoneNumberDelete = sc.nextLine();
                            contactManage.deleteContact(phoneNumberDelete);
                            break;
                        case 5:
                            System.out.println("Nhập số điện thoại hoặc nhóm cần tìm: ");
                            String inputFind = sc.nextLine();
                            ArrayList<Contact> contacts = contactManage.findContact(inputFind);
                            if (!contacts.isEmpty()) {
                                for (Contact contact : contacts) {
                                    System.out.println(contact);
                                }
                            } else {
                                System.out.println("Không có số điện thoại hoặc nhóm này trong danh bạ");
                            }
                            break;
                        case 6:
//                    ArrayList<Contact> contactsRead = contactManage.readCSV();
                            for (Contact contact : contactManage.readCSV()) {
                                System.out.println(contact);
                            }
                            break;
                        case 7:
                            contactManage.writeCSV(contactManage.displayContactList());
                            break;
                        case 8:
                            System.out.println("Hẹn gặp lại");
                            check = false;
                            break;
                        default:
                            System.out.println("Không có lựa chọn đó");
                            break;
                    }
                } while (choice != 8);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
