using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace FastCleanAPI.Migrations
{
    public partial class deleteRepetition : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_ReportsFuncionarios_Utilizador_ReviewedId",
                table: "ReportsFuncionarios");

            migrationBuilder.DropForeignKey(
                name: "FK_ReportsFuncionarios_Utilizador_ReviewerId",
                table: "ReportsFuncionarios");

            migrationBuilder.DropPrimaryKey(
                name: "PK_ReportsFuncionarios",
                table: "ReportsFuncionarios");

            migrationBuilder.RenameTable(
                name: "ReportsFuncionarios",
                newName: "ReviewsFuncionarios");

            migrationBuilder.RenameColumn(
                name: "email",
                table: "Utilizador",
                newName: "Email");

            migrationBuilder.RenameIndex(
                name: "IX_ReportsFuncionarios_ReviewerId",
                table: "ReviewsFuncionarios",
                newName: "IX_ReviewsFuncionarios_ReviewerId");

            migrationBuilder.RenameIndex(
                name: "IX_ReportsFuncionarios_ReviewedId",
                table: "ReviewsFuncionarios",
                newName: "IX_ReviewsFuncionarios_ReviewedId");

            migrationBuilder.AddColumn<int>(
                name: "NumHorasPrevistas",
                table: "Marcacoes",
                type: "int",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddPrimaryKey(
                name: "PK_ReviewsFuncionarios",
                table: "ReviewsFuncionarios",
                column: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_ReviewsFuncionarios_Utilizador_ReviewedId",
                table: "ReviewsFuncionarios",
                column: "ReviewedId",
                principalTable: "Utilizador",
                principalColumn: "Id",
                onDelete: ReferentialAction.NoAction);

            migrationBuilder.AddForeignKey(
                name: "FK_ReviewsFuncionarios_Utilizador_ReviewerId",
                table: "ReviewsFuncionarios",
                column: "ReviewerId",
                principalTable: "Utilizador",
                principalColumn: "Id",
                onDelete: ReferentialAction.NoAction);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_ReviewsFuncionarios_Utilizador_ReviewedId",
                table: "ReviewsFuncionarios");

            migrationBuilder.DropForeignKey(
                name: "FK_ReviewsFuncionarios_Utilizador_ReviewerId",
                table: "ReviewsFuncionarios");

            migrationBuilder.DropPrimaryKey(
                name: "PK_ReviewsFuncionarios",
                table: "ReviewsFuncionarios");

            migrationBuilder.DropColumn(
                name: "NumHorasPrevistas",
                table: "Marcacoes");

            migrationBuilder.RenameTable(
                name: "ReviewsFuncionarios",
                newName: "ReportsFuncionarios");

            migrationBuilder.RenameColumn(
                name: "Email",
                table: "Utilizador",
                newName: "email");

            migrationBuilder.RenameIndex(
                name: "IX_ReviewsFuncionarios_ReviewerId",
                table: "ReportsFuncionarios",
                newName: "IX_ReportsFuncionarios_ReviewerId");

            migrationBuilder.RenameIndex(
                name: "IX_ReviewsFuncionarios_ReviewedId",
                table: "ReportsFuncionarios",
                newName: "IX_ReportsFuncionarios_ReviewedId");

            migrationBuilder.AddPrimaryKey(
                name: "PK_ReportsFuncionarios",
                table: "ReportsFuncionarios",
                column: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_ReportsFuncionarios_Utilizador_ReviewedId",
                table: "ReportsFuncionarios",
                column: "ReviewedId",
                principalTable: "Utilizador",
                principalColumn: "Id",
                onDelete: ReferentialAction.NoAction);

            migrationBuilder.AddForeignKey(
                name: "FK_ReportsFuncionarios_Utilizador_ReviewerId",
                table: "ReportsFuncionarios",
                column: "ReviewerId",
                principalTable: "Utilizador",
                principalColumn: "Id",
                onDelete: ReferentialAction.NoAction);
        }
    }
}
