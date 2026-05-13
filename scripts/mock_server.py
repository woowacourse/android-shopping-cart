from http.server import BaseHTTPRequestHandler, HTTPServer
import json
from pathlib import Path
from urllib.parse import urlparse


PRODUCT_PRICES = [
    10000,
    12000,
    10000,
    12000,
    10000,
    12000,
    99800,
    10000,
    12000,
    10000,
    12000,
    10000,
    12000,
    99800,
    10000,
    12000,
    10000,
    12000,
    10000,
    12000,
    99800,
    10000,
    12000,
    99800,
]

DRAWABLE_DIR = Path(__file__).resolve().parent.parent / "app" / "src" / "main" / "res" / "drawable"


def product_id_for(index):
    return f"00000000-0000-0000-0000-0000000000{index:02x}"


def image_url_for(host, index):
    return f"http://{host}/images/product-{index}.png"


def build_products(host):
    return [
        {
            "id": product_id_for(index),
            "name": f"상품 {index}",
            "price": PRODUCT_PRICES[index - 1],
            "imageUrl": image_url_for(host, index),
        }
        for index in range(1, 25)
    ]


def build_cart_items(products):
    return [
        {
            "id": "cart-item-1",
            "quantity": 5,
            "product": products[0],
        },
        {
            "id": "cart-item-2",
            "quantity": 1,
            "product": products[1],
        },
    ]


def image_path_for(index):
    return DRAWABLE_DIR / f"product_image{index}.png"


class Handler(BaseHTTPRequestHandler):
    def do_GET(self):
        parsed_path = urlparse(self.path)
        path = parsed_path.path
        host = self.headers.get("Host", "localhost:12345")
        products = build_products(host)

        if path == "/products":
            self._send_json(200, products)
            return

        if path.startswith("/products/"):
            product_id = path.removeprefix("/products/")
            product = next((item for item in products if item["id"] == product_id), None)
            if product is None:
                self._send_json(404, {"message": "product not found"})
                return
            self._send_json(200, product)
            return

        if path == "/cart-items":
            self._send_json(200, build_cart_items(products))
            return

        if path.startswith("/images/product-") and path.endswith(".png"):
            product_index = path.removeprefix("/images/product-").removesuffix(".png")
            if not product_index.isdigit():
                self._send_json(404, {"message": "image not found"})
                return

            index = int(product_index)
            if index < 1 or index > 24:
                self._send_json(404, {"message": "image not found"})
                return

            image_path = image_path_for(index)
            if not image_path.exists():
                self._send_json(404, {"message": "image not found"})
                return

            self._send_file(200, image_path, "image/png")
            return

        self._send_json(404, {"message": "not found"})

    def log_message(self, format, *args):
        return

    def _send_json(self, status_code, body):
        encoded_body = json.dumps(body).encode()
        self.send_response(status_code)
        self.send_header("Content-Type", "application/json")
        self.send_header("Content-Length", str(len(encoded_body)))
        self.end_headers()
        self.wfile.write(encoded_body)

    def _send_file(self, status_code, file_path, content_type):
        encoded_body = file_path.read_bytes()
        self.send_response(status_code)
        self.send_header("Content-Type", content_type)
        self.send_header("Content-Length", str(len(encoded_body)))
        self.end_headers()
        self.wfile.write(encoded_body)


if __name__ == "__main__":
    server = HTTPServer(("0.0.0.0", 12345), Handler)
    print("Mock server running at http://0.0.0.0:12345")
    server.serve_forever()
