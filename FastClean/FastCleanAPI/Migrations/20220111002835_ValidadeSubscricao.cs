using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace FastCleanAPI.Migrations
{
    public partial class ValidadeSubscricao : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "ValidadeSubscricao",
                table: "Utilizador",
                type: "nvarchar(max)",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "ValidadeSubscricao",
                table: "Utilizador");
        }
    }
}
