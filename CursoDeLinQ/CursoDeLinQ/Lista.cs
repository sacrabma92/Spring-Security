namespace CursoDeLinQ
{
    internal class Lista
    {
        public static string ObtenerTexto(string option, string texto)
        {
            switch (option)
            {
                case "Nombre":
                    return string.Format("{0,-25}", texto);
                case "Identificacion":
                    return string.Format(" {0,-14}", texto);
                case "Edad":
                    return string.Format(" {0,-4}", texto);
                case "Procedimiento":
                    return string.Format(" {0,-14}", texto);
                case "Id":
                    return string.Format(" {0,-4}", texto);
                default:
                    return null;
            }
            return "";
        }

        public static void MostrarMedicos(IEnumerable<dynamic> medicos)
        {
            if(medicos.Count() == 0)
            {
                Console.WriteLine("\n\tSin registros");
                return;
            }

            var propiedades = medicos.First().GetType().GetProperties();
            string encabezado = "\n\t";

            foreach(var propiedad in propiedades) 
            {
                encabezado += ObtenerTexto(propiedad.Name, propiedad.Name);
            }

            Console.Write(encabezado);
            int tamEncabezado = encabezado.Length;

            Console.WriteLine("\n\t{0}", new string('-', tamEncabezado));

            foreach(var medico in medicos)
            {
                string fila = "\t";

                foreach (var propiedad in propiedades)
                {
                    fila += ObtenerTexto(propiedad.Name, propiedad.GetValue(medico).ToString());
                }
                Console.WriteLine(fila);
            }
        }
    }
}
