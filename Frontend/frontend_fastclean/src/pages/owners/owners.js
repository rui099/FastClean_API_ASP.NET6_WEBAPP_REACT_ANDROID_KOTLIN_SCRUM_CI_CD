import React, { Component} from "react";
import './owners.css';

const owners = () => {
    return (
        <div class="main">
            <label><h3><br/>&nbsp;Responsáveis pelo projeto FastClean &copy; :</h3></label><hr/><br/>
            <div>
                <span class="texto">Este projeto nasceu da necessidade de implementar um sistema seguro, rápido e de qualidade <br/>
                &nbsp;relacionado com serviços de limpeza em território nacional. <br/><br/>
                &nbsp; Tal ideia permite que todas as pessoas que necessitem de agendar marcações para serviços <br/> &nbsp; de limpeza o possam fazer a partir de uma aplicação mobile e 
                possam também ter algum tipo<br/>
                &nbsp; de interação pelo site. <br/><br/>
                &nbsp; Além disso, este projeto visa favorecer pessoas desempregadas ou que precisem de ganhar <br/>
                &nbsp; um rendimento extra. Isto porque, qualquer pessoa pode candidatar-se a tais serviços <br/>
                &nbsp; precisando de comprovar, naturalmente a sua identidade bem como outro tipo de documentos. &nbsp; <br/><br/>
                &nbsp; Como responsáveis destacam-se: <br/><br/> 
                <li class="membros"> Bruno Costa  </li>
                <li  class="membros"> Célio Macedo </li>
                <li  class="membros"> João Leite </li>
                <li  class="membros"> Rafael Costa </li>
                <li  class="membros"> Rui Soares </li> <br/>
                 </span>
            </div>    
        </div>
    );
};
export default owners;