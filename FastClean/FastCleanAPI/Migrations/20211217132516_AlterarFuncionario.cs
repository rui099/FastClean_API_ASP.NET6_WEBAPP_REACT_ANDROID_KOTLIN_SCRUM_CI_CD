using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace FastCleanAPI.Migrations
{
    public partial class AlterarFuncionario : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<bool>(
                name: "Banido",
                table: "Utilizador",
                type: "bit",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<int>(
                name: "Idade",
                table: "Utilizador",
                type: "int",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<double>(
                name: "Preco",
                table: "Utilizador",
                type: "float",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "TipoLimpeza",
                table: "Utilizador",
                type: "int",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Banido",
                table: "Utilizador");

            migrationBuilder.DropColumn(
                name: "Idade",
                table: "Utilizador");

            migrationBuilder.DropColumn(
                name: "Preco",
                table: "Utilizador");

            migrationBuilder.DropColumn(
                name: "TipoLimpeza",
                table: "Utilizador");
        }
    }
}
