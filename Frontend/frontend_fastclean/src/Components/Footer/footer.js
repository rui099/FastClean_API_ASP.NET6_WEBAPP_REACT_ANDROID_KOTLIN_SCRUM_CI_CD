import React from "react";
import { FaFacebook, FaInstagramSquare, FaGooglePlay } from 'react-icons/fa';
import { Link } from 'react-router-dom';
import './FooterElements.css';

const Footer = () => {
return (
	<div class = "footer">
	<footer>
		<h2 class="tituloFooter">FastClean &copy; </h2>
	
		<div class="titles">
			<h3 class="subtitulo">Fundadores FastClean</h3>
			<h4><Link to='/owners'>Responsáveis</Link></h4>
		</div>

		<div class="titles">
			<h3 class="subtitulo">Aplicação Mobile</h3>
			<a class="ico" href="#"><FaGooglePlay/></a> 
		
		</div>

		<div  class="titles">
			<h3 class="subtitulo">Redes Sociais</h3>
		
			<a class="iconsRS" href="#"><FaFacebook/></a> 
			<a class="iconsRS" href="#"><FaInstagramSquare/></a>
		</div>
		
	</footer>
	</div>
);
};
export default Footer;