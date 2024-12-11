package coding;

import coding.ENUMS.GROUP_STATUS;

import java.time.LocalDate;

public class Member extends User{
    private GROUP_STATUS group_status;

    public Member(String userId, String password, String userName, String email, LocalDate dateOfBirth, String status) {
        super(userId,password,userName,email,dateOfBirth,status);
        group_status = GROUP_STATUS.NOTMEMBER;
    }

    public GROUP_STATUS getGroup_status() {
        return group_status;
    }

    public void setGroup_status(GROUP_STATUS group_status) {
        this.group_status = group_status;
    }
}
