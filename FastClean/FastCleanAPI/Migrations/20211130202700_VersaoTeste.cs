using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace FastCleanAPI.Migrations
{
    public partial class VersaoTeste : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Chats_Utilizador_ClienteId",
                table: "Chats");

            migrationBuilder.DropForeignKey(
                name: "FK_Chats_Utilizador_FuncionarioId",
                table: "Chats");

            migrationBuilder.DropForeignKey(
                name: "FK_Marcacoes_Utilizador_ClienteId",
                table: "Marcacoes");

            migrationBuilder.DropForeignKey(
                name: "FK_Marcacoes_Utilizador_FuncionarioId",
                table: "Marcacoes");

            migrationBuilder.DropForeignKey(
                name: "FK_Mensagens_Chats_ChatId",
                table: "Mensagens");

            migrationBuilder.DropForeignKey(
                name: "FK_Mensagens_Utilizador_SenderId",
                table: "Mensagens");

            migrationBuilder.DropForeignKey(
                name: "FK_ReportsClientes_Utilizador_ReportedId",
                table: "ReportsClientes");

            migrationBuilder.DropForeignKey(
                name: "FK_ReportsClientes_Utilizador_ReporterId",
                table: "ReportsClientes");

            migrationBuilder.DropForeignKey(
                name: "FK_ReportsFuncionario_Utilizador_ReportedId",
                table: "ReportsFuncionario");

            migrationBuilder.DropForeignKey(
                name: "FK_ReportsFuncionario_Utilizador_ReporterId",
                table: "ReportsFuncionario");

            migrationBuilder.DropForeignKey(
                name: "FK_ReportsFuncionarios_Utilizador_ReviewedId",
                table: "ReportsFuncionarios");

            migrationBuilder.DropForeignKey(
                name: "FK_ReportsFuncionarios_Utilizador_ReviewerId",
                table: "ReportsFuncionarios");

            migrationBuilder.DropForeignKey(
                name: "FK_ReviewsClientes_Utilizador_ReviewedId",
                table: "ReviewsClientes");

            migrationBuilder.DropForeignKey(
                name: "FK_ReviewsClientes_Utilizador_ReviewerId",
                table: "ReviewsClientes");

            migrationBuilder.DropForeignKey(
                name: "FK_Utilizador_Moradas_MoradaId",
                table: "Utilizador");

            migrationBuilder.AddForeignKey(
                name: "FK_Chats_Utilizador_ClienteId",
                table: "Chats",
                column: "ClienteId",
                principalTable: "Utilizador",
                principalColumn: "Id",
                onDelete: ReferentialAction.NoAction);

            migrationBuilder.AddForeignKey(
                name: "FK_Chats_Utilizador_FuncionarioId",
                table: "Chats",
                column: "FuncionarioId",
                principalTable: "Utilizador",
                principalColumn: "Id",
                onDelete: ReferentialAction.NoAction);

            migrationBuilder.AddForeignKey(
                name: "FK_Marcacoes_Utilizador_ClienteId",
                table: "Marcacoes",
                column: "ClienteId",
                principalTable: "Utilizador",
                principalColumn: "Id",
                onDelete: ReferentialAction.NoAction);

            migrationBuilder.AddForeignKey(
                name: "FK_Marcacoes_Utilizador_FuncionarioId",
                table: "Marcacoes",
                column: "FuncionarioId",
                principalTable: "Utilizador",
                principalColumn: "Id",
                onDelete: ReferentialAction.NoAction);

            migrationBuilder.AddForeignKey(
                name: "FK_Mensagens_Chats_ChatId",
                table: "Mensagens",
                column: "ChatId",
                principalTable: "Chats",
                principalColumn: "Id",
                onDelete: ReferentialAction.NoAction);

            migrationBuilder.AddForeignKey(
                name: "FK_Mensagens_Utilizador_SenderId",
                table: "Mensagens",
                column: "SenderId",
                principalTable: "Utilizador",
                principalColumn: "Id",
                onDelete: ReferentialAction.NoAction);

            migrationBuilder.AddForeignKey(
                name: "FK_ReportsClientes_Utilizador_ReportedId",
                table: "ReportsClientes",
                column: "ReportedId",
                principalTable: "Utilizador",
                principalColumn: "Id",
                onDelete: ReferentialAction.NoAction);

            migrationBuilder.AddForeignKey(
                name: "FK_ReportsClientes_Utilizador_ReporterId",
                table: "ReportsClientes",
                column: "ReporterId",
                principalTable: "Utilizador",
                principalColumn: "Id",
                onDelete: ReferentialAction.NoAction);

            migrationBuilder.AddForeignKey(
                name: "FK_ReportsFuncionario_Utilizador_ReportedId",
                table: "ReportsFuncionario",
                column: "ReportedId",
                principalTable: "Utilizador",
                principalColumn: "Id",
                onDelete: ReferentialAction.NoAction);

            migrationBuilder.AddForeignKey(
                name: "FK_ReportsFuncionario_Utilizador_ReporterId",
                table: "ReportsFuncionario",
                column: "ReporterId",
                principalTable: "Utilizador",
                principalColumn: "Id",
                onDelete: ReferentialAction.NoAction);

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

            migrationBuilder.AddForeignKey(
                name: "FK_ReviewsClientes_Utilizador_ReviewedId",
                table: "ReviewsClientes",
                column: "ReviewedId",
                principalTable: "Utilizador",
                principalColumn: "Id",
                onDelete: ReferentialAction.NoAction);

            migrationBuilder.AddForeignKey(
                name: "FK_ReviewsClientes_Utilizador_ReviewerId",
                table: "ReviewsClientes",
                column: "ReviewerId",
                principalTable: "Utilizador",
                principalColumn: "Id",
                onDelete: ReferentialAction.NoAction);

            migrationBuilder.AddForeignKey(
                name: "FK_Utilizador_Moradas_MoradaId",
                table: "Utilizador",
                column: "MoradaId",
                principalTable: "Moradas",
                principalColumn: "Id",
                onDelete: ReferentialAction.NoAction);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Chats_Utilizador_ClienteId",
                table: "Chats");

            migrationBuilder.DropForeignKey(
                name: "FK_Chats_Utilizador_FuncionarioId",
                table: "Chats");

            migrationBuilder.DropForeignKey(
                name: "FK_Marcacoes_Utilizador_ClienteId",
                table: "Marcacoes");

            migrationBuilder.DropForeignKey(
                name: "FK_Marcacoes_Utilizador_FuncionarioId",
                table: "Marcacoes");

            migrationBuilder.DropForeignKey(
                name: "FK_Mensagens_Chats_ChatId",
                table: "Mensagens");

            migrationBuilder.DropForeignKey(
                name: "FK_Mensagens_Utilizador_SenderId",
                table: "Mensagens");

            migrationBuilder.DropForeignKey(
                name: "FK_ReportsClientes_Utilizador_ReportedId",
                table: "ReportsClientes");

            migrationBuilder.DropForeignKey(
                name: "FK_ReportsClientes_Utilizador_ReporterId",
                table: "ReportsClientes");

            migrationBuilder.DropForeignKey(
                name: "FK_ReportsFuncionario_Utilizador_ReportedId",
                table: "ReportsFuncionario");

            migrationBuilder.DropForeignKey(
                name: "FK_ReportsFuncionario_Utilizador_ReporterId",
                table: "ReportsFuncionario");

            migrationBuilder.DropForeignKey(
                name: "FK_ReportsFuncionarios_Utilizador_ReviewedId",
                table: "ReportsFuncionarios");

            migrationBuilder.DropForeignKey(
                name: "FK_ReportsFuncionarios_Utilizador_ReviewerId",
                table: "ReportsFuncionarios");

            migrationBuilder.DropForeignKey(
                name: "FK_ReviewsClientes_Utilizador_ReviewedId",
                table: "ReviewsClientes");

            migrationBuilder.DropForeignKey(
                name: "FK_ReviewsClientes_Utilizador_ReviewerId",
                table: "ReviewsClientes");

            migrationBuilder.DropForeignKey(
                name: "FK_Utilizador_Moradas_MoradaId",
                table: "Utilizador");

            migrationBuilder.AddForeignKey(
                name: "FK_Chats_Utilizador_ClienteId",
                table: "Chats",
                column: "ClienteId",
                principalTable: "Utilizador",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_Chats_Utilizador_FuncionarioId",
                table: "Chats",
                column: "FuncionarioId",
                principalTable: "Utilizador",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_Marcacoes_Utilizador_ClienteId",
                table: "Marcacoes",
                column: "ClienteId",
                principalTable: "Utilizador",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_Marcacoes_Utilizador_FuncionarioId",
                table: "Marcacoes",
                column: "FuncionarioId",
                principalTable: "Utilizador",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_Mensagens_Chats_ChatId",
                table: "Mensagens",
                column: "ChatId",
                principalTable: "Chats",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_Mensagens_Utilizador_SenderId",
                table: "Mensagens",
                column: "SenderId",
                principalTable: "Utilizador",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_ReportsClientes_Utilizador_ReportedId",
                table: "ReportsClientes",
                column: "ReportedId",
                principalTable: "Utilizador",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_ReportsClientes_Utilizador_ReporterId",
                table: "ReportsClientes",
                column: "ReporterId",
                principalTable: "Utilizador",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_ReportsFuncionario_Utilizador_ReportedId",
                table: "ReportsFuncionario",
                column: "ReportedId",
                principalTable: "Utilizador",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_ReportsFuncionario_Utilizador_ReporterId",
                table: "ReportsFuncionario",
                column: "ReporterId",
                principalTable: "Utilizador",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_ReportsFuncionarios_Utilizador_ReviewedId",
                table: "ReportsFuncionarios",
                column: "ReviewedId",
                principalTable: "Utilizador",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_ReportsFuncionarios_Utilizador_ReviewerId",
                table: "ReportsFuncionarios",
                column: "ReviewerId",
                principalTable: "Utilizador",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_ReviewsClientes_Utilizador_ReviewedId",
                table: "ReviewsClientes",
                column: "ReviewedId",
                principalTable: "Utilizador",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_ReviewsClientes_Utilizador_ReviewerId",
                table: "ReviewsClientes",
                column: "ReviewerId",
                principalTable: "Utilizador",
                principalColumn: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_Utilizador_Moradas_MoradaId",
                table: "Utilizador",
                column: "MoradaId",
                principalTable: "Moradas",
                principalColumn: "Id");
        }
    }
}
