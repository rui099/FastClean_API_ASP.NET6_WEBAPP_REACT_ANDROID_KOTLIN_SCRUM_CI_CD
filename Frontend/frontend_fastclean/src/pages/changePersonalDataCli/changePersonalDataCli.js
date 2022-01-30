import React, { Component} from "react";
import './changePersonalDataCli.css';
import ImgFundo from '../../images/img2.jpg';
import api from "../../services/api";
import { getCargo} from "../../services/auth";
import {FaPlusCircle, FaEdit} from "react-icons/fa";
import axios from "axios";

var pathname = window.location.pathname;
var ID = pathname.split('/')[2];
var email = pathname.split('/')[3];

class changePersonalDataCli extends Component{
  
  state = {
    
    Imagem: "",
    Telemovel: "",
    Rua: "",
    Numero: "",
    CodPostal: "",
    Freguesia: "",
    Distrito: "",
    Concelho: "",
    errorI: "",
    sucessI: "",
    errorCC: "",
    sucessCC: "",
    errorCad: "",
    sucessCad: "",
    errorM: "",
    sucessM: "",
    errorC: "",
    sucessC: ""
  };

  editImagem = async e =>{
    e.preventDefault();

    const { Imagem } = this.state;
  
    const form = new FormData();

    form.append("Imagem", this.state.Imagem);

      try {
              await axios.put("https://localhost:7067/api/Clientes/AtualizarClienteImagem/" + ID + "/" + email,  form );
              this.setState({ sucessI: "A Imagem foi alterada com Sucesso!" });
              document.getElementById("sucesso").style.color = "green";
              setTimeout(function(){
                document.getElementById("sucesso").innerHTML = '';
              }, 2000);
              document.getElementById("IMGForm").reset();
      } catch (err) {
        this.setState({ error: "Erro ao editar imagem. Atenção à extensão do ficheiro!" });
      }
  };

  editContacto = async e =>{
    e.preventDefault();

    const { Telemovel } = this.state;
  
    try {
              await axios.put("https://localhost:7067/api/alterarContacto/" + ID + "/" + Telemovel);
              this.setState({ sucessC: "O Contacto foi alterada com Sucesso!" });
              document.getElementById("sucesso").style.color = "green";
              setTimeout(function(){
                document.getElementById("sucesso").innerHTML = '';
              }, 2000);
              document.getElementById("ContactForm").reset();
      } catch (err) {
        this.setState({ errorC: "Erro ao editar contacto!" });
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
      document.getElementById("MoradaForm").reset();
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
              await axios.put("https://localhost:7067/api/Clientes/AtualizarClienteCcfile/" + ID + "/" + email,  form2 );
              this.setState({ sucessCC: "O Cartão de Cidadão foi alterado com Sucesso!" });
              document.getElementById("sucesso").style.color = "green";
              setTimeout(function(){
                document.getElementById("sucesso").innerHTML = '';
              }, 2000);
              document.getElementById("CCForm").reset();
      } catch (err) {
        this.setState({ errorCC: "Erro ao editar cartão de cidadão. Atenção à extensão do ficheiro!" });
      }
  };

  editCadastro = async e =>{
    e.preventDefault();

    const { Imagem } = this.state;
  
    const form3 = new FormData();

    form3.append("Imagem", this.state.Imagem);

      try {
              await axios.put("https://localhost:7067/api/Clientes/AtualizarClienteCadastro/" + ID + "/" + email,  form3 );
              this.setState({ sucessCad: "O Cadastro foi alterado com Sucesso!" });
              document.getElementById("sucesso").style.color = "green";
              setTimeout(function(){
                document.getElementById("sucesso").innerHTML = '';
              }, 2000);
              document.getElementById("CadastroForm").reset();
      } catch (err) {
        this.setState({ errorCad: "Erro ao editar cadastro. Atenção à extensão do ficheiro!" });
      }
  };

render() {
return (
    <div className="displayInfo"> 
       <image class="Img" src={ImgFundo}/>
        <h2> Editar Dados Pessoais </h2><br/>
      <hr/><br/>
       <fieldset class="editData2">
       <legend><b>Imagem</b></legend>
           <form id="IMGForm" onSubmit={this.editImagem}> 
                <label className="titulosCampos"><b>Fotografia:</b> </label>
                <input name="Imagem" className="editDataInput2" type="file" onChange={e => this.setState({ Imagem: e.target.files[0] })} /> 
                <button className="btnEditData2"> <FaEdit /> </button>
                <div>
                    {this.state.sucessI && <p id="sucesso"><b>{this.state.sucessI}</b></p>}
                    {this.state.errorI && <p id="err"><b>{this.state.errorI}</b></p>} 
                </div>  
          </form>
       </fieldset><br/>
       <fieldset class="editData2">
       <legend><b>Contacto</b></legend>
           <form id="ContactForm"  onSubmit={this.editContacto}> 
                
                 <label className="titulosCampos"> <b>Telemóvel:</b> </label>
                 <input className="editDataInput2" name="Telemovel" type="tel" pattern="9[1236][0-9]{7}|2[1-9][0-9]{7}" 
                 onChange={e => this.setState({ Telemovel: e.target.value })}/> 
                 <button className="btnEditData2"> <FaEdit /> </button>
                 
                <div>
                  {this.state.sucessC && <p id="sucesso"><b>{this.state.sucessC}</b></p>}
                  {this.state.errorC && <p id="err"><b>{this.state.errorC}</b></p>} 
                </div>
           </form>
       </fieldset><br/>
       <fieldset class="editData2">
       <legend><b>Morada</b></legend>
           <form id="MoradaForm" onSubmit={this.editMorada}> 
          <table>
          <tr>
           <td className="titulosCampos"><label> <b>Rua:</b> </label></td>
           <td><input className="editDataInput2" type="text" name="Rua"  onChange={e => this.setState({ Rua: e.target.value })}/> <br/></td>
          </tr>
          <tr>
            <td className="titulosCampos"> <label> <b>Número da Porta:</b> </label></td>
            <td> <input className="editDataInput2" type="number" name="Numero"  onChange={e => this.setState({ Numero: e.target.value })} /> <br/></td>
          </tr>
          <tr>
            <td className="titulosCampos"> <label> <b>Código Postal:</b> </label></td>
            <td> <input className="editDataInput2" type="text" 
               name="CodPostal"  onChange={e => this.setState({ CodigoPostal: e.target.value })} /> <br/> </td>
          </tr>
          <tr>
            <td className="titulosCampos">  <label> <b>Freguesia:</b> </label></td>
            <td>  <input className="editDataInput2" type="text" name="Freguesia"  onChange={e => this.setState({ Freguesia: e.target.value })}/> <br/> </td>
          </tr>
          <tr>
            <td className="titulosCampos"> <label> <b>Concelho:</b> </label></td>
            <td> <input className="editDataInput2" type="text" name="Concelho"  onChange={e => this.setState({ Concelho: e.target.value })}/>  <br/> </td>
          </tr>
          <tr>
            <td className="titulosCampos"> <label> <b>Distrito:</b> </label></td>
            <td>  <input className="editDataInput2" type="text" name="Distrito"  onChange={e => this.setState({ Distrito: e.target.value })}/>  </td>
             <button id="btnED2" > <FaEdit /> </button>
          </tr>
          
           <div>
                {this.state.sucessM && <p id="sucesso"><b>{this.state.sucessM}</b></p>}
                {this.state.errorM && <p id="err"><b>{this.state.errorM}</b></p>} 
            </div>
            </table> 
          </form>
       </fieldset><br/>
       <fieldset class="editData2">
       <legend><b>Ficheiros</b></legend>
           <form id="CCForm" onSubmit={this.editCC}> 
                <label className="titulosCampos"> <b>C.Cidadão:</b> </label>
                <input className="editDataInput2" type="file" name="CcFile" onChange={e => this.setState({ Imagem: e.target.files[0] })}  /> 
                <button className="btnEditData2"> <FaEdit />  </button>
                <div>
                  {this.state.sucessCC && <p id="sucesso"><b>{this.state.sucessCC}</b></p>}
                  {this.state.errorCC && <p id="err"><b>{this.state.errorCC}</b></p>} 
                </div>
           </form>
           <form id="CadastroForm" onSubmit={this.editCadastro}>
                <label className="titulosCampos"><b> Cadastro:</b> </label>
                &nbsp;&nbsp;<input  className="editDataInput2" type="file" name="Cadastro" onChange={e => this.setState({ Imagem: e.target.files[0] })}  /> 
                &nbsp;&nbsp;<button className="btnEditData2"> <FaEdit />  </button>
                <div>
                  {this.state.sucessCad && <p id="sucesso"><b>{this.state.sucessCad}</b></p>}
                  {this.state.errorCad && <p id="err"><b>{this.state.errorCad}</b></p>} 
                </div>
           </form>
       </fieldset><br/><br/>
</div>
);
}
}
export default changePersonalDataCli;