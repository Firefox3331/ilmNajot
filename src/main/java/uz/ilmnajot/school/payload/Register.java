package uz.ilmnajot.school.payload;

public record Register(
        Long id,
        String firstName,
        String lastName,
        String email,
        String username,
        String password,
        String rePassword,
        String phoneNumber,
        Long schoolId,
        String gender
) {
}
