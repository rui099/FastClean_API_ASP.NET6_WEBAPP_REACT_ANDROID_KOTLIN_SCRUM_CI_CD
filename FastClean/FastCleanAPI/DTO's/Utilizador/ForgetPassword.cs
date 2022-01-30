using System.ComponentModel.DataAnnotations;

namespace FastCleanAPI.DTO_s.Utilizador
{
    public class ForgetPassword
    {
        [Required]
        public string Email { get; set; }

        [Required]
        public string NewPassword { get; set; }

        [Required] 

        public string Confirm_Password { get; set; }
    }
}
