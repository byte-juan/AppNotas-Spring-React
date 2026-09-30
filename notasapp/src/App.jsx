import { useState, useEffect } from 'react'
import heroImg from './assets/hero.png'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import './App.css'

function App() {
  const [notas, setNotas] = useState([])
  const [titulo, setTitulo] = useState("")
  const [descripcion, setDescripcion] = useState("")
  const [editandoId, setEditandoId] = useState(null)

  useEffect(() => {
    fetchNotas()
  }, [])

  const fetchNotas = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/notas')
      const notasData = await response.json();
      setNotas(notasData)
    } catch (error) {
      console.error('Error al obtener las notas', error)
    }
  }

  const crearNota = async () => {
    if (!titulo || !descripcion) {
      return alert("Campos vacíos");
    }

    try {
      if (editandoId) {
        const res = await fetch(`http://localhost:8080/api/notas/${editandoId}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({ 
            titulo: titulo, 
            contenido: descripcion 
          })
        });

        if (!res.ok) {
          throw new Error(`Error en el servidor: ${res.status}`);
        }

        setEditandoId(null);

      } else {
        const res = await fetch('http://localhost:8080/api/notas', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({ 
            titulo: titulo, 
            contenido: descripcion 
          })
        });

        if (!res.ok) {
          throw new Error(`Error en el servidor: ${res.status}`);
        }
      }

      setTitulo("");
      setDescripcion("");
      await fetchNotas();

    } catch (error) {
      console.error('Error al guardar la nota:', error);
    }
  };

  
  const eliminarNota = async (id) => {
    try {
      const res = await fetch(`http://localhost:8080/api/notas/${id}`, {
        method: 'DELETE'
      });

      if (res.ok) {
        fetchNotas(); 
      } else {
        console.error('Error al eliminar la nota');
      }
    } catch (error) {
      console.error('Error al eliminar la nota:', error);
    }
  }

  
  const prepararEdicion = (nota) => {
    setEditandoId(nota.id)
    setTitulo(nota.titulo)
    setDescripcion(nota.contenido) 
  }

  return (
    <>
      <section id="center">
        <div className="hero">
          <img src={heroImg} className="base" width="170" height="179" alt="" />
          <img src={reactLogo} className="framework" alt="React logo" />
          <img src={viteLogo} className="vite" alt="Vite logo" />
        </div>
        <div>
          <h1>App de Notas</h1>
          <p>
            Esta es una aplicación de notas creada con React, JavaScript y Spring Boot
          </p>
        </div>
      </section>

      <div className="form">
        <input
          type="text"
          placeholder="Título"
          value={titulo}
          onChange={(e) => setTitulo(e.target.value)}
        />

        <input
          type="text"
          placeholder="Descripción"
          value={descripcion}
          onChange={(e) => setDescripcion(e.target.value)}
        />
        
        <button onClick={crearNota}>
          {editandoId ? "Editar nota" : "Guardar nota"}
        </button>

        <div className="notas">
          {notas.map((nota) => {
            return (
              <div className="nota" key={nota.id}>
                <h3>{nota.titulo}</h3>

                {}
                <p>{nota.contenido}</p>

                <div className="acciones">
                  <button 
                    className="editar"
                    onClick={() => prepararEdicion(nota)}
                  >
                    Editar
                  </button>

                  <button 
                    className="eliminar"
                    onClick={() => eliminarNota(nota.id)}
                  >
                    Eliminar
                  </button>
                </div>
              </div>
            )
          })}
        </div>  
      </div>

      <div className="ticks"></div>
      <section id="spacer"></section>
    </>
  )
}

export default App