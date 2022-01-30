using System.ComponentModel.DataAnnotations;

namespace FastCleanAPI.DTO_s.ChatDTO
{
    public class SendMensagemDTO
    {
        public int MensagemID { get; set; }

        [Required]
        public int Sender { get; set; }

        [Required]
        public string Text { get; set; }

        [Required]
        public int ChatId { get; set; }
    }
}
