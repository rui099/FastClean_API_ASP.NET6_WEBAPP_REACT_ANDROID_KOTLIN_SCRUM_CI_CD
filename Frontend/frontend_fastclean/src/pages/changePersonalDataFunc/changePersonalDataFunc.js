import React, { Component} from "react";
import './changePersonalDataFunc.css';
import ImgFundo from '../../images/img2.jpg';
import api from "../../services/api";
import { getCargo} from "../../services/auth";
import {FaPlusCircle, FaEdit} from "react-icons/fa";
import axios from "axios";

var pathname = window.location.pathname;
var ID = pathname.split('/')[2];
var email = pathname.split('/')[3];

class changePersonalDataFunc extends Component{
  
  state = {
    Imagem: "",
    Telemovel: "",
    Rua: "",
    Numero: "",
    CodPostal: "",
    Freguesia: "",
    Distrito: "",
    Concelho: "",
    newPrice: "",
    errorI2: "",
    sucessI2: "",
    errorC2: "",
    sucessC2: "",
    errorM2: "",
    sucessM2: "",
    errorFCC2: "",
    sucessFCC2: "",
    errorFHM2: "",
    sucessFHM2: "",
    sucessFCCond2:"",
    errorFCCond2: "" ,
    sucessFCad2: "",
    errorFCad2: "",
    sucessFCV2: "",
    errorFCV2: "",
    errorP: "",
    sucessP: ""
  };

  editImagem = async e =>{
    
    e.preventDefault();

    const { Imagem } = this.state;
  
    const form = new FormData();

    form.append("Imagem", this.state.Imagem);

      try {
              await axios.put("https://localhost:7067/api/Funcionarios/AtualizarFuncionarioImagem/" + ID + "/" + email,  form );
              this.setState({ sucessI2: "A Imagem foi alterada com Sucesso!" });
              document.getElementById("sucesso").style.color = "green";
              setTimeout(function(){
                document.getElementById("sucesso").innerHTML = '';
              }, 2000);
              document.getElementById("IMGForm2").reset();
      } catch (err) {
        this.setState({ errorI2: "Erro ao editar imagem. Atenção à extensão do ficheiro!" });
      }
  };

  editContacto = async e =>{
    
    e.preventDefault();

    const { Telemovel } = this.state;
  
    try {
              await axios.put("https://localhost:7067/api/alterarContacto/" + ID + "/" + Telemovel);
              this.setState({ sucessC2: "O Contacto foi alterada com Sucesso!" });
              document.getElementById("sucesso").style.color = "green";
              setTimeout(function(){
                document.getElementById("sucesso").innerHTML = '';
              }, 2000);
              document.getElementById("ContactForm2").reset();
      } catch (err) {
        this.setState({ errorC2: "Erro ao editar contacto!" });
      }
  };

  editMorada = async e =>{
    
    e.preventDefault();

    const {
      Rua,
      Numero,
      CodigoPostal,
      Freguesia,
      Concelho,
      Distrito
    } = this.state;

    try {
      await api.put("alterarMorada/" + ID , {Rua, Numero, CodigoPostal, Freguesia, Concelho, Distrito});
      this.setState({ sucessM: "A Morada foi alterada com Sucesso!" });
      document.getElementById("sucesso").style.color = "green";
      setTimeout(function(){
        document.getElementById("sucesso").innerHTML = '';
      }, 2000);
      document.getElementById("MoradaForm2").reset();
    } catch (err) {
    this.setState({ errorM: "Erro ao editar morada!" });
    }

  };

  editCC = async e =>{

    e.preventDefault();

    const { Imagem } = this.state;
  
    const form2 = new FormData();

    form2.append("Imagem", this.state.Imagem);

      try {
              await axios.put("https://localhost:7067/api/Funcionarios/AtualizarFuncionarioCcfile/" + ID + "/" + email,  form2 );
              this.setState({ sucessFCC2: "O Cartão de Cidadão foi alterado com Sucesso!" });
              document.getElementById("sucesso").style.color = "green";
              setTimeout(function(){
                document.getElementById("sucesso").innerHTML = '';
              }, 2000);
              document.getElementById("CCForm2").reset();
      } catch (err) {
        this.setState({ errorFCC2: "Erro ao editar cartão de cidadão. Atenção à extensão do ficheiro!" });
      }
  };

  editHistoricoMedico = async e =>{

    e.preventDefault();

    const { Imagem } = this.state;
  
    const form3 = new FormData();

    form3.append("Imagem", this.state.Imagem);

      try {
              await axios.put("https://localhost:7067/api/Funcionarios/AtualizarFuncionarioHistoricoMedico/" + ID + "/" + email,  form3 );
              this.setState({ sucessFHM2: "O Histórico Médico foi alterado com Sucesso!" });
              document.getElementById("sucesso").style.color = "green";
              setTimeout(function(){
                document.getElementById("sucesso").innerHTML = '';
              }, 2000);
              document.getElementById("HMForm").reset();
      } catch (err) {
        this.setState({ errorFHM2: "Erro ao editar histórico médico. Atenção à extensão do ficheiro!" });
      }

  };

  editCadastro = async e =>{

    e.preventDefault();

    const { Imagem } = this.state;
  
    const form4 = new FormData();

    form4.append("Imagem", this.state.Imagem);

      try {
              await axios.put("https://localhost:7067/api/Funcionarios/AtualizarFuncionarioCadastro/" + ID + "/" + email,  form4 );
              this.setState({ sucessFCad2: "O Cadastro foi alterado com Sucesso!" });
              document.getElementById("sucesso").style.color = "green";
              setTimeout(function(){
                document.getElementById("sucesso").innerHTML = '';
              }, 2000);
              document.getElementById("CadastroForm2").reset();
      } catch (err) {
        this.setState({ errorFCad2: "Erro ao editar cadastro. Atenção à extensão do ficheiro!" });
      }
    
  };

  editCartaConducao = async e =>{
    
    e.preventDefault();

    const { Imagem } = this.state;
  
    const form5 = new FormData();

    form5.append("Imagem", this.state.Imagem);

      try {
              await axios.put("https://localhost:7067/api/Funcionarios/AtualizarFuncionarioCartaDeConducao/" + ID + "/" + email,  form5 );
              this.setState({ sucessFCCond2: "A Carta Condução foi alterado com Sucesso!" });
              document.getElementById("sucesso").style.color = "green";
              setTimeout(function(){
                document.getElementById("sucesso").innerHTML = '';
              }, 2000);
              document.getElementById("CartaCondForm").reset();
      } catch (err) {
        this.setState({ errorFCCond2: "Erro ao editar carta de condução. Atenção à extensão do ficheiro!" });
      }
  };

  editCvFile = async e =>{

    e.preventDefault();

    const { Imagem } = this.state;
  
    const form6 = new FormData();

    form6.append("Imagem", this.state.Imagem);

      try {
              await axios.put("https://localhost:7067/api/Funcionarios/AtualizarFuncionarioCvfile/" + ID + "/" + email,  form6 );
              this.setState({ sucessFCV2: "O Currículo foi alterado com Sucesso!" });
              document.getElementById("sucesso").style.color = "green";
              setTimeout(function(){
                document.getElementById("sucesso").innerHTML = '';
              }, 2000);
              document.getElementById("CvForm").reset();
      } catch (err) {
        this.setState({ errorFCV2: "Erro ao editar currículo. Atenção à extensão do ficheiro!" });
      }
  };

  editPreco = async e =>{
    
    e.preventDefault();

    const { newPrice } = this.state;
  
    try {
              await axios.put("https://localhost:7067/api/Funcionarios/alterarPreco/" + ID + "/" + newPrice);
              this.setState({ sucessP: "O Preço foi alterado com Sucesso!" });
              document.getElementById("sucesso").style.color = "green";
              setTimeout(function(){
                document.getElementById("sucesso").innerHTML = '';
              }, 2000);
              document.getElementById("precoForm").reset();
      } catch (err) {
        this.setState({ errorP: "Erro ao editar preço!" });
      }
};


render() {
return (
    <div className="displayInfo"> 
       <image class="Img" src={ImgFundo}/>
        <h2> Editar Dados Pessoais </h2><br/>
      <hr/><br/>
       <fieldset class="editData">
       <legend><b>Imagem</b></legend>
           <form id="IMGForm2" onSubmit={this.editImagem}> 
                <label className="titulosCampos"><b>Fotografia:</b> </label>&emsp;&emsp;&emsp;
                <input name="Imagem" className="editDataInput" type="file" onChange={e => this.setState({ Imagem: e.target.files[0] })}/> 
                <button className="btnEditData"> <FaEdit /> </button>
                <div>
                    {this.state.sucessI2 && <p id="sucesso"><b>{this.state.sucessI2}</b></p>}
                    {this.state.errorI2 && <p id="err"><b>{this.state.errorI2}</b></p>} 
                </div>
          </form>
       </fieldset><br/>
       <fieldset class="editData">
       <legend><b>Contacto</b></legend>
           <form id="ContactForm2" onSubmit={this.editContacto}> 
                <label> <b>Telemóvel:</b> </label>&emsp;&emsp;&emsp;&emsp;
                <input name="Telemovel" className="editDataInput"  pattern="9[1236][0-9]{7}|2[1-9][0-9]{7}" 
                 onChange={e => this.setState({ Telemovel: e.target.value })} />
                 <button className="btnEditData"> <FaEdit /> </button>
               
                <div>
                  {this.state.sucessC2 && <p id="sucesso"><b>{this.state.sucessC2}</b></p>}
                  {this.state.errorC2 && <p id="err"><b>{this.state.errorC2}</b></p>} 
                </div>
           </form>
       </fieldset> <br/>
       <fieldset class="editData">
       <legend><b>Morada</b></legend>
       <form id="MoradaForm2" onSubmit={this.editMorada}> 
          <table>
          <tr>
           <td className="titulosCampos2"><label> <b>Rua:</b> </label></td>
           <td> &emsp; <input className="editDataInput" type="text" name="Rua"  onChange={e => this.setState({ Rua: e.target.value })}/> <br/></td>
          </tr>
          <tr>
            <td className="titulosCampos2"> <label> <b>Número da Porta:</b> </label></td>
            <td> &emsp; <input className="editDataInput" type="number" name="Numero"  onChange={e => this.setState({ Numero: e.target.value })} /> <br/></td>
          </tr>
          <tr>
            <td className="titulosCampos2"> <label> <b>Código Postal:</b> </label></td>
            <td> &emsp; <input className="editDataInput" type="text" 
               name="CodPostal"  onChange={e => this.setState({ CodigoPostal: e.target.value })} /> <br/> </td>
          </tr>
          <tr>
            <td className="titulosCampos2">  <label> <b>Freguesia:</b> </label></td>
            <td> &emsp;  <input className="editDataInput" type="text" name="Freguesia"  onChange={e => this.setState({ Freguesia: e.target.value })}/> <br/> </td>
          </tr>
          <tr>
            <td className="titulosCampos2"> <label> <b>Concelho:</b> </label></td>
            <td> &emsp; <input className="editDataInput" type="text" name="Concelho"  onChange={e => this.setState({ Concelho: e.target.value })}/>  <br/> </td>
          </tr>
          <tr>
            <td className="titulosCampos2"> <label> <b>Distrito:</b> </label></td>
            <td> &emsp;  <input className="editDataInput" type="text" name="Distrito"  onChange={e => this.setState({ Distrito: e.target.value })}/>  </td>
             <button id="btnED" > <FaEdit /> </button>
          </tr>
          
           <div>
                {this.state.sucessM2 && <p id="sucesso"><b>{this.state.sucessM2}</b></p>}
                {this.state.errorM2 && <p id="err"><b>{this.state.errorM2}</b></p>} 
            </div>
            </table>
          </form>
       </fieldset><br/>
       <fieldset class="editData">
       <legend><b>Ficheiros</b></legend>
           <form id="CCForm2" onSubmit={this.editCC}> 
                <label><b> Cartão Cidadão: </b></label>&emsp;
                <input name="CcFile" className="editDataInput" type="file" onChange={e => this.setState({ Imagem: e.target.files[0] })} /> 
               
                <button className="btnEditData"> <FaEdit />  </button>
                <div>
                  {this.state.sucessFCC2 && <p id="sucesso"><b>{this.state.sucessFCC2}</b></p>}
                  {this.state.errorFCC2 && <p id="err"><b>{this.state.errorFCC2}</b></p>} 
                </div>
          </form>
          <form id="HMForm" onSubmit={this.editHistoricoMedico}>
                <label><b> Histórico Médico:</b> </label> 
                <input name="HistoricoMedico"  className="editDataInput" type="file" onChange={e => this.setState({ Imagem: e.target.files[0] })}/> 
                <button   className="btnEditData"> <FaEdit /></button>
                <div>
                  {this.state.sucessFHM2 && <p id="sucesso"><b>{this.state.sucessFHM2}</b></p>}
                  {this.state.errorFHM2 && <p id="err"><b>{this.state.errorFHM2}</b></p>} 
                </div>
          </form>
          <form id="CartaCondForm" onSubmit={this.editCartaConducao}>
                <label> <b>Carta Condução:</b> </label>&nbsp;
                <input name="CartaDeConducao"  className="editDataInput" type="file" onChange={e => this.setState({ Imagem: e.target.files[0] })} /> 
                <button className="btnEditData" name="CartaDeConducao"> <FaEdit />  </button>
                <div>
                  {this.state.sucessFCCond2 && <p id="sucesso"><b>{this.state.sucessFCCond2}</b></p>}
                  {this.state.errorFCCond2 && <p id="err"><b>{this.state.errorFCCond2 }</b></p>} 
                </div>
          </form>
          <form id="CadastroForm2" onSubmit={this.editCadastro}>
                <label><b> Cadastro Pessoal:</b> </label>
                <input name="Cadastro"  className="editDataInput" type="file" onChange={e => this.setState({ Imagem: e.target.files[0] })} /> 
                <button className="btnEditData"> <FaEdit />  </button>
                <div>
                  {this.state.sucessFCad2 && <p id="sucesso"><b>{this.state.sucessFCad2}</b></p>}
                  {this.state.errorFCad2 && <p id="err"><b>{this.state.errorFCad2}</b></p>} 
                </div>
          </form>
          <form id="CvForm" onSubmit={this.editCvFile}>
                <label><b> Curriculum Vitae:</b> </label>&nbsp;
                <input name="CvFile"  className="editDataInput" type="file" onChange={e => this.setState({ Imagem: e.target.files[0] })} /> 
                <button className="btnEditData" name="CvFile"> <FaEdit />  </button>
                <div>
                  {this.state.sucessFCV2 && <p id="sucesso"><b>{this.state.sucessFCV2}</b></p>}
                  {this.state.errorFCV2 && <p id="err"><b>{this.state.errorFCV2}</b></p>} 
                </div>
          </form>
       </fieldset> <br/>
       <fieldset class="editData">
       <legend><b>Preço(€)</b></legend>
           <form id="precoForm" onSubmit={this.editPreco}> 
                <label><b> Preço por Serviço:</b> </label>
                <input name="Preco" className="editDataInput" type="text" onChange={e => this.setState({ newPrice: e.target.value })} /> 
                <button className="btnEditData"> <FaEdit /> </button>
            <div>
                {this.state.sucessP && <p id="sucesso"><b>{this.state.sucessP}</b></p>}
                {this.state.errorP && <p id="err"><b>{this.state.errorP}</b></p>} 
            </div>
           </form>
       </fieldset><br/><br/>
</div>
);
}
}
export default changePersonalDataFunc;